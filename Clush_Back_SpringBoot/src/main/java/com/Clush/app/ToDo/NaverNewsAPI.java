package com.Clush.app.ToDo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NaverNewsAPI {

    private static final ConfigLoader configLoader = new ConfigLoader();
    private static final String API_ID = configLoader.getNaverSearchAPIId();
    private static final String API_KEY = configLoader.getNaverSearchAPIKey();

    public JSONObject getNews(String keyword) {
        String clientId = API_ID; // 애플리케이션 클라이언트 아이디값
        String clientSecret = API_KEY; // 애플리케이션 클라이언트 시크릿값
        try {
            // 검색어 인코딩
            String text = URLEncoder.encode(keyword, "UTF-8");

            // API URL 생성
            String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=10&start=1&sort=date"; 
  
            // URL 연결 설정
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); 
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
   
            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { 
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { 
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            // 응답 처리
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) { 
                response.append(inputLine);
            }
            br.close(); 

            // 응답 출력
    		return new JSONObject(response.toString());
        } catch (Exception e) {
            // 예외 처리
            System.out.println(e);
        }
		return null;

    }
}
