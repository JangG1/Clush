package com.Clush.app.TestService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Clush.app.ToDo.NaverNewsAPI;
import com.Clush.app.ToDo.ToDoController;

@ExtendWith(MockitoExtension.class)
class ToDoControllerTest {

    @Mock
    private NaverNewsAPI naverNewsAPI;

    @InjectMocks
    private ToDoController toDoController;

    @Test
    @DisplayName("getNews - 네이버 뉴스 API 호출 테스트")
    void testGetNews() {
        // Given
        String keyword = "테스트";
        
        try {
        JSONObject mockResponse = new JSONObject();
        mockResponse.put("title", "테스트 뉴스 제목");
        mockResponse.put("link", "https://news.naver.com");
        
        when(naverNewsAPI.getNews(keyword)).thenReturn(mockResponse);

        // When
        Map<String, Object> response = toDoController.getNews(keyword);

        // Then
        assertNotNull(response);
        assertTrue(response.containsKey("data"));
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        assertEquals("테스트 뉴스 제목", data.get("title"));
        assertEquals("https://news.naver.com", data.get("link"));
        } catch (JSONException e) {
            fail("JSONException 발생: " + e.getMessage()); // 테스트 실패 처리
        }
        // 네이버 API가 1번 호출되었는지 검증
        verify(naverNewsAPI, times(1)).getNews(keyword);
    }
}
