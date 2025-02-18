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

import com.Clush.app.ToDo.ConfigLoader;
import com.Clush.app.ToDo.NaverNewsAPI;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class NaverNewsAPITest {

    @InjectMocks
    private NaverNewsAPI naverNewsAPI;

    @Mock
    private ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        when(configLoader.getNaverSearchAPIId()).thenReturn("dummy-id");
        when(configLoader.getNaverSearchAPIKey()).thenReturn("dummy-key");
    }

    @Test
    @DisplayName("getNews - 네이버 뉴스 API 호출 성공 테스트")
    void testGetNews() throws IOException {
        // Given
        String keyword = "테스트";
        try {
        JSONObject mockResponse = new JSONObject();
        mockResponse.put("title", "테스트 뉴스 제목");
        mockResponse.put("link", "https://news.naver.com");

        // 네트워크 요청을 직접 수행하지 않도록 가짜 응답 반환
        NaverNewsAPI spyApi = spy(naverNewsAPI);
        doReturn(mockResponse).when(spyApi).getNews(keyword);

        // When
        JSONObject result = spyApi.getNews(keyword);

        // Then
        assertNotNull(result);
        assertEquals("테스트 뉴스 제목", result.getString("title"));
        assertEquals("https://news.naver.com", result.getString("link"));
        } catch (JSONException e) {
            fail("JSONException 발생: " + e.getMessage()); // 테스트 실패 처리
        }
    }
}
