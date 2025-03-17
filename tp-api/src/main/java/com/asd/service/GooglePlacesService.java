package com.osj.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service
public class GooglePlacesService {

    @Value("${google.api.key}")  // application.properties에서 API 키 관리
    private String apiKey;
    private String language = "ko";

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    public Map<String, String> searchPlace(String query) {
        try {
            String url = BASE_URL + "?query=" + query + "&key=" + apiKey + "&language=" + language;
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("API Response: " + response.getBody());
            
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray results = jsonResponse.optJSONArray("results");
            
            if (results == null || results.length() == 0) {
            	System.out.println("No results found");
                return null; // 검색 결과 없음
            }

            JSONObject place = results.getJSONObject(0); // 첫 번째 검색 결과 가져오기

            // 필요한 정보 추출(json에서 특정 필드만 추출하여 관리에 용이함)
            Map<String, String> placeData = new HashMap<>();
            placeData.put("name", place.optString("name", "N/A"));
            placeData.put("address", place.optString("formatted_address", "N/A"));
            placeData.put("lat", String.valueOf(place.getJSONObject("geometry").getJSONObject("location").optDouble("lat", 0.0)));
            placeData.put("lng", String.valueOf(place.getJSONObject("geometry").getJSONObject("location").optDouble("lng", 0.0)));

            System.out.println("Place Data: " + placeData);
            return placeData;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환
        }
    }
}
