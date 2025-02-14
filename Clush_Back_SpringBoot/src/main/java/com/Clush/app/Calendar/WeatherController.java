package com.Clush.app.Calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Clush.app.Repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService = new WeatherService();

    // 날씨 정보를 반환하는 API
    @GetMapping("/weather")
    public String getWeather() {
 
    	String result =weatherService.getWeather(); // 날씨 정보 반환
    	System.out.println(result);
        return result;
    }
}
