package com.Clush.app.TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Clush.app.ToDo.ConfigLoader;
import com.Clush.app.ToDo.NaverNewsAPI;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class NaverNewsAPITest {

    private static final Logger log = LoggerFactory.getLogger(NaverNewsAPITest.class);

    @InjectMocks
    private NaverNewsAPI naverNewsAPI;

    @Mock
    private ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        log.info("설정 초기화 시작");
        when(configLoader.getNaverSearchAPIId()).thenReturn("dummy-id");
        when(configLoader.getNaverSearchAPIKey()).thenReturn("dummy-key");
        log.info("설정 초기화 완료 - API ID: {}, API Key: {}", "dummy-id", "dummy-key");
    }

    @Test
    @DisplayName("getNews - 네이버 뉴스 API 호출 성공 테스트")
    void testGetNews() throws IOException {
        log.info("네이버 뉴스 API 호출 테스트 시작");
        String keyword = "테스트";
        log.info("검색어: {}", keyword);
        try {
            JSONObject mockResponse = new JSONObject();
            mockResponse.put("title", "테스트 뉴스 제목");
            mockResponse.put("link", "https://news.naver.com");
            log.info("모의 응답 생성 완료: {}", mockResponse.toString());

            NaverNewsAPI spyApi = spy(naverNewsAPI);
            doReturn(mockResponse).when(spyApi).getNews(keyword);
            log.info("Mock 설정 완료 - getNews 호출 시 가짜 응답 반환");

            JSONObject result = spyApi.getNews(keyword);
            log.info("API 응답: {}", result.toString());

            assertNotNull(result);
            assertEquals("테스트 뉴스 제목", result.getString("title"));
            assertEquals("https://news.naver.com", result.getString("link"));
            log.info("테스트 성공 - 예상 결과와 일치");
        } catch (JSONException e) {
            log.error("JSONException 발생: {}", e.getMessage());
            fail("JSONException 발생: " + e.getMessage());
        }
    }
}
