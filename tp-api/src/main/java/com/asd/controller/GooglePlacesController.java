package com.asd.controller;

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

import com.asd.repository.GoogleApiRepository;
import com.asd.service.GoogleDirectionsService;
import com.asd.service.GooglePlacesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class GooglePlacesController {

    private final GooglePlacesService googlePlacesService;
    private final GoogleDirectionsService googleDirectionsService;
    private final GoogleApiRepository googleApiRepository;

    // Google Places API 검색 후 Redis에 List 형태로 저장
    @PostMapping("/search/{day}")
    public String searchAndSavePlace(@PathVariable String day, @RequestParam String query) {
        Map<String, String> placeData = googlePlacesService.searchPlace(query);

        if (placeData == null) {
            return "No results found!";
        }

        String key = day;
        String placeJson = new org.json.JSONObject(placeData).toString();

        googleApiRepository.savePlace(key, placeJson);

        return "Success: Place data saved in Redis for " + day + "!";
    }

    // 저장된 모든 장소 정보 조회
    @GetMapping("/get/{day}")
    public List<String> getPlaces(@PathVariable String day) {
        return googleApiRepository.getPlaces(day);
    }

    // 장소 순서 변경 (리스트 순서 업데이트)
    @PutMapping("/updateOrder/{day}")
    public String updatePlacesOrder(@PathVariable String day, @RequestBody List<String> updatedPlaces) {
    	googleApiRepository.updatePlacesOrder(day, updatedPlaces);
        return "Success: Places order updated for " + day + "!";
    }

    // 특정 장소 삭제
    @DeleteMapping("/delete/{day}")
    public String deletePlace(@PathVariable String day, @RequestParam String name) {
        List<String> places = googleApiRepository.getPlaces(day);

        for (String place : places) {
            if (place.contains(name)) {
            	googleApiRepository.deletePlace(day, place);
                return "Success: Place " + name + " deleted from " + day + "!";
            }
        }
        return "Error: Place not found!";
    }

    // 전체 장소 삭제
    @DeleteMapping("/deleteAll/{day}")
    public String deleteAllPlaces(@PathVariable String day) {
    	googleApiRepository.deleteAllPlaces(day);
        return "Success: All places deleted for " + day + "!";
    }

    // 길찾기 결과 저장 (출발지, 목적지만 사용)
    @GetMapping("/directions/{day}")
    public String getDirections(@PathVariable String day, @RequestParam String origin, @RequestParam String destination) {
        String route = origin + "->" + destination;
        String directionsResult = googleDirectionsService.getDirections(origin, destination);
        
        // 예시: 점수는 현재 시간을 사용하거나 특정 기준을 사용할 수 있습니다.
        double score = System.currentTimeMillis();  // 예: 현재 시간을 score로 사용
        googleApiRepository.saveDirections(day, route + " : " + directionsResult, score);

        return directionsResult;
    }

    // 저장된 길찾기 결과 조회
    @GetMapping("/directionsResult/{day}")
    public Set<String> getDirectionsResult(@PathVariable String day) {
        return googleApiRepository.getDirections(day);  // Sorted Set에서 길찾기 결과 조회
    }

    // 특정 길찾기 결과 삭제
    @DeleteMapping("/directions/delete/{day}")
    public String deleteDirection(@PathVariable String day, @RequestParam String route) {
    	googleApiRepository.deleteDirection(day, route);
        return "Success: Deleted route " + route + " from " + day;
    }

    // 전체 길찾기 결과 삭제
    @DeleteMapping("/directions/deleteAll/{day}")
    public String deleteAllDirections(@PathVariable String day) {
    	googleApiRepository.deleteAllDirections(day);
        return "Success: Deleted all directions for " + day;
    }
}
