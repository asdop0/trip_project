package com.osj.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osj.DTO.PlaceDto;
import com.osj.service.GoogleApiService;
import com.osj.service.GoogleDirectionsService;
import com.osj.service.GooglePlacesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class GooglePlacesController {

    private final GooglePlacesService googlePlacesService;
    private final GoogleDirectionsService googleDirectionsService;
    private final GoogleApiService googleApiService;  // Service 주입

    // Google Places API 검색 후 Redis에 저장
    @PostMapping("/search/{day}")
    public String searchAndSavePlace(@PathVariable String day, @RequestParam String query) {
        Map<String, String> placeData = googlePlacesService.searchPlace(query);
        if (placeData == null) {
            return "No results found!";
        }

        String placeJson = new org.json.JSONObject(placeData).toString();
        googleApiService.savePlace(day, placeJson);  // Service 호출

        return "Success: Place data saved in Redis for " + day + "!";
    }

    // 저장된 장소 조회
    @GetMapping("/get/{day}")
    public List<PlaceDto> getPlaces(@PathVariable String day) {
        return googleApiService.getPlaces(day);
    }

    // 장소 순서 변경
    @PutMapping("/updateOrder/{day}")
    public String updatePlacesOrder(@PathVariable String day, @RequestBody List<String> updatedPlaces) {
        googleApiService.updatePlacesOrder(day, updatedPlaces);
        return "Success: Places order updated for " + day + "!";
    }

    // 특정 장소 삭제
    @DeleteMapping("/delete/{day}")
    public String deletePlace(@PathVariable String day, @RequestParam String name) {
        googleApiService.deletePlace(day, name);
        return "Success: Place " + name + " deleted from " + day + "!";
    }

    // 전체 장소 삭제
    @DeleteMapping("/deleteAll/{day}")
    public String deleteAllPlaces(@PathVariable String day) {
        googleApiService.deleteAllPlaces(day);
        return "Success: All places deleted for " + day + "!";
    }

    // 길찾기 결과 저장
    @GetMapping("/directions/{day}")
    public String getDirections(@PathVariable String day, @RequestParam String origin, @RequestParam String destination) {
        String route = origin + "->" + destination;
        String directionsResult = googleDirectionsService.getDirections(origin, destination);
        
        double score = System.currentTimeMillis();
        googleApiService.saveDirections(day, route + " : " + directionsResult, score);  // ✅ Service 호출

        return directionsResult;
    }

    // 저장된 길찾기 결과 조회
    @GetMapping("/directionsResult/{day}")
    public Set<String> getDirectionsResult(@PathVariable String day) {
        return googleApiService.getDirections(day);
    }

    // 특정 길찾기 결과 삭제
    @DeleteMapping("/directions/delete/{day}")
    public String deleteDirection(@PathVariable String day, @RequestParam String route) {
        googleApiService.deleteDirection(day, route);
        return "Success: Deleted route " + route + " from " + day;
    }

    // 전체 길찾기 결과 삭제
    @DeleteMapping("/directions/deleteAll/{day}")
    public String deleteAllDirections(@PathVariable String day) {
        googleApiService.deleteAllDirections(day);
        return "Success: Deleted all directions for " + day;
    }
}

