package com.Clush.app.ToDo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NaverNewsAPI {

    private static final ConfigLoader configLoader = new ConfigLoader();
    private static final String API_ID = configLoader.getNaverSearchAPIId();
    private static final String API_KEY = configLoader.getNaverSearchAPIKey();

    // 뉴스 검색 및 썸네일 가져오는 메소드
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

            // JSON 응답 객체로 변환
            JSONObject jsonResponse = new JSONObject(response.toString());

            // 뉴스 목록 가져오기
            JSONArray items = jsonResponse.getJSONArray("items");

            // 각 뉴스에 대해 썸네일을 추출하고 JSON 응답에 추가
            for (int i = 0; i < items.length(); i++) {
                JSONObject newsItem = items.getJSONObject(i);
                String link = newsItem.getString("link");
                String thumbnail = getThumbnailFromLink(link);
                newsItem.put("thumbnail", thumbnail);  // 썸네일 추가
            }

            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 기사 링크에서 썸네일을 추출하는 메소드
    private String getThumbnailFromLink(String url) {
        try {
            // URL에서 HTML 페이지 가져오기
            Document doc = Jsoup.connect(url).get();

            // Open Graph meta 태그에서 썸네일 이미지 추출 (og:image)
            Element metaImage = doc.select("meta[property=og:image]").first();
            if (metaImage != null) {
                return metaImage.attr("content");
            }

            // <img> 태그에서 첫 번째 이미지 추출
            Element imgTag = doc.select("img").first();
            if (imgTag != null) {
                return imgTag.absUrl("src");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // 썸네일이 없으면 null 반환
    }
}
