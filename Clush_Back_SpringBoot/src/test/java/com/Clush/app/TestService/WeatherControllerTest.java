package com.Clush.app.TestService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.Clush.app.Calendar.WeatherController;
import com.Clush.app.Calendar.WeatherService;

class WeatherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    @DisplayName("GET /weather - 날씨 API 호출 성공")
    void testGetWeather() throws Exception {
        // given
        String mockResponse = "{ \"2024-02-18\": { \"min_temp\": 20, \"max_temp\": 30, \"avg_temp\": 25, \"humidity\": 60, \"weather\": \"clear sky\" } }";
        when(weatherService.getWeather()).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/weather"))
            .andExpect(status().isOk())
            .andExpect(content().json(mockResponse));
    }
}
