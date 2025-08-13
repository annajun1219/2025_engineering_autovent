package com.example._2025_engineering_autovent.scheduler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class AirQualityScheduler {

    private final RestTemplate restTemplate = new RestTemplate();

    // API Key를 직접 코드에 입력
    private final String key = "56777844746a737931323173576c5668";
    // 내 서버의 "미세먼지 모드 가동 API" 주소
    private final String dustModeApiUrl = "http://localhost:8080/dust";

    //기준값
    private static final int PM10_THRESHOLD = 81;   // 미세먼지
    private static final int PM25_THRESHOLD = 36;   // 초미세먼지

    // 매일 오후 7시 30분 실행
    @Scheduled(cron = "0 59 19 * * *")
    public void fetchSeoulAirQuality() {
        // 호출할 실제 API URL
        String apiUrl = String.format(
                "http://openapi.seoul.go.kr:8088/%s/json/ListAirQualityByDistrictService/1/5/111131",
                key
        );

        try {
            String response = restTemplate.getForObject(apiUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode rowArray = rootNode
                    .path("ListAirQualityByDistrictService")
                    .path("row");

            if (rowArray.isArray()) {
                for (JsonNode row : rowArray) {
                    String pm10Value = row.path("PM10").asText(); // 필드명은 실제 API 응답에 맞게 변경
                    String pm25Value = row.path("PM25").asText(); // 필드명은 실제 API 응답에 맞게 변경

                    System.out.println("미세먼지(PM10): " + pm10Value);
                    System.out.println("초미세먼지(PM2.5): " + pm25Value);
                }
            } else {
                System.out.println("데이터가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
