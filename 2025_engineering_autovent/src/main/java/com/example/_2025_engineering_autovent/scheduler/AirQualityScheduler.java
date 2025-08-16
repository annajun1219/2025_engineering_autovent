package com.example._2025_engineering_autovent.scheduler;

import com.example._2025_engineering_autovent.dto.DustRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
                    int pm10Value = row.path("PM10").asInt(); // 필드명은 실제 API 응답에 맞게 변경
                    int pm25Value = row.path("PM25").asInt(); // 필드명은 실제 API 응답에 맞게 변경

                    System.out.println("미세먼지(PM10): " + pm10Value);
                    System.out.println("초미세먼지(PM2.5): " + pm25Value);

                    if (pm10Value >= PM10_THRESHOLD || pm25Value >= PM25_THRESHOLD) {
                        sendDustModeRequest();
                    }
                }


            }
         else {
            System.out.println("데이터가 존재하지 않습니다.");
         }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendDustModeRequest() {
        DustRequest dustRequest = new DustRequest();
        dustRequest.setLocation_id(1); // 실제 location_id
        dustRequest.setWindow_id("1");   // 실제 window_id
        dustRequest.setMode("dust");   // 미세먼지 모드
        dustRequest.setTime_stamp(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        try {
            // POST 요청으로 DustController 호출
            String result = restTemplate.postForObject(dustModeApiUrl, dustRequest, String.class);
            System.out.println("Dust API 호출 결과: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
