package com.asd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleDirectionsService {

    @Value("${google.api.key}")  // application.properties에서 API 키 불러오기
    private String apiKey;
    
    private String mode = "transit";
    private String language = "ko";
    private String alter = "true";

    private static final String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";

    public String getDirections(String origin, String destination) {
        if (origin == null || destination == null) {
            return "Error: 출발지와 도착지를 입력해야 합니다.";
        }

        
        // Google Directions API 호출 URL (경유지 없이)
        String url = DIRECTIONS_URL + "?origin=" + origin +
                     "&destination=" + destination +
                     "&mode=" + mode +
                     "&alternative=" + alter +
                     "&language=" + language +
                     "&key=" + apiKey;

        // API 호출
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONObject jsonResponse = new JSONObject(response.getBody());

            // 🚀 API 응답이 실패하거나 경로가 없을 경우 처리
            if (!jsonResponse.getString("status").equals("OK")) {
                return "Error: " + jsonResponse.getString("status");
            }

            return jsonResponse.toString(4);  // JSON Pretty Print
        } catch (Exception e) {
            return "Error: API 요청 실패 - " + e.getMessage();
        }
    }
}
