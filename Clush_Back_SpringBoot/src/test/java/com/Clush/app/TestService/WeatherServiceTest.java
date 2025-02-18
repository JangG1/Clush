package com.Clush.app.TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Clush.app.Calendar.WeatherService;

class WeatherServiceTest {

    private WeatherService weatherService;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService();
    }

    @Test
    @DisplayName("getWeather - 정상적인 API 응답 처리")
    void testGetWeather_Success() throws Exception {
        // given
        String mockJsonResponse = "{ \"list\": [{ \"dt\": 1700000000, \"main\": { \"temp\": 25, \"temp_min\": 20, \"temp_max\": 30, \"humidity\": 60 }, \"weather\": [{ \"description\": \"clear sky\" }] }] }";

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(mockJsonResponse);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        // when
        String result = weatherService.getWeather();

        // then
        assertNotNull(result);
        assertTrue(result.contains("\"min_temp\":20"));
        assertTrue(result.contains("\"max_temp\":30"));
        assertTrue(result.contains("\"avg_temp\":25"));
        assertTrue(result.contains("\"weather\":\"clear sky\""));
    }

    @Test
    @DisplayName("parseWeatherData - JSON 데이터 파싱 테스트")
    void testParseWeatherData() {
        // given
        String mockJsonResponse = "{ \"list\": [{ \"dt\": 1700000000, \"main\": { \"temp\": 25, \"temp_min\": 20, \"temp_max\": 30, \"humidity\": 60 }, \"weather\": [{ \"description\": \"clear sky\" }] }] }";

        // when
        String parsedData = weatherService.parseWeatherData(mockJsonResponse);

        try {
            JSONObject jsonObject = new JSONObject(parsedData);
            String dateKey = jsonObject.keys().next().toString(); // 첫 번째 날짜 키 추출

            // then
            assertNotNull(jsonObject);
            assertTrue(jsonObject.has(dateKey));
            assertEquals(20, jsonObject.getJSONObject(dateKey).getDouble("min_temp"));
            assertEquals(30, jsonObject.getJSONObject(dateKey).getDouble("max_temp"));
            assertEquals(25, jsonObject.getJSONObject(dateKey).getDouble("avg_temp"));
            assertEquals("clear sky", jsonObject.getJSONObject(dateKey).getString("weather"));
        } catch (JSONException e) {
            fail("JSONException 발생: " + e.getMessage()); // 테스트 실패 처리
        }
    }


}
