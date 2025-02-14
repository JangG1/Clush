package com.Clush.app.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.Clush.app.Calendar.ConfigLoader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject; // JSON 파싱을 위한 라이브러리

public class WeatherService {
	private static final ConfigLoader configLoader = new ConfigLoader();
	private static final String API_KEY = configLoader.getWeatherAPIKey(); // 발급받은 API 키
	private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast"; // OpenWeatherMap API URL

	public static void main(String[] args) {

	}

	public String getWeather() {
		double latitude = 37.5665; // 서울의 위도
		double longitude = 126.9784; // 서울의 경도
		try {
			// API 요청 URL 생성
			String url = String.format("%s?lat=%f&lon=%f&exclude=current,minutely,hourly,alerts&appid=%s&units=metric",
					BASE_URL, latitude, longitude, API_KEY);

			// HttpClient 객체 생성
			HttpClient client = HttpClient.newHttpClient();

			// HTTP GET 요청 생성
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			// HTTP 요청 보내기 및 응답 받기
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// 응답 처리 (날씨 정보 파싱)
			if (response.statusCode() == 200) {
				String responseBody = response.body();
				return parseWeatherData(responseBody); // 파싱된 날씨 정보를 반환
			} else {
				return "날씨 정보를 가져오는 데 실패했습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "날씨 정보를 가져오는 데 실패했습니다.";
		}
	}

	public String parseWeatherData(String data) {
	    JSONObject jsonObject = new JSONObject(data);
	    JSONArray dailyWeather = jsonObject.getJSONArray("list");

	    // LinkedHashMap을 사용하여 JSON 순서를 유지
	    Map<String, JSONObject> weatherMap = new LinkedHashMap<>();

	    for (int i = 0; i < dailyWeather.length(); i++) {
	        JSONObject daily = dailyWeather.getJSONObject(i);

	        // UNIX 타임스탬프를 날짜로 변환
	        long timestamp = daily.getLong("dt");
	        String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp * 1000));

	        // 온도 정보
	        JSONObject temp = daily.getJSONObject("main");
	        double dayTemp = temp.getDouble("temp");
	        double minTemp = temp.getDouble("temp_min");
	        double maxTemp = temp.getDouble("temp_max");
	        int humidity = temp.getInt("humidity");

	        // 날씨 설명
	        JSONArray weatherArray = daily.getJSONArray("weather");
	        String description = weatherArray.getJSONObject(0).getString("description");

	        // JSON 객체 생성
	        JSONObject weatherDetails = new JSONObject();
	        weatherDetails.put("min_temp", minTemp);
	        weatherDetails.put("max_temp", maxTemp);
	        weatherDetails.put("avg_temp", dayTemp);
	        weatherDetails.put("humidity", humidity);
	        weatherDetails.put("weather", description);

	        // 날짜를 key 값으로 설정하여 JSON 데이터 구성
	        weatherMap.put(dateStr, weatherDetails);
	    }

	    // LinkedHashMap을 JSONObject로 변환하여 반환
	    return new JSONObject(weatherMap).toString();
	}
}
