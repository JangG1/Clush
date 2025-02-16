package com.Clush.app.ToDo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
public class ToDoController {
    
	@Autowired
    private NaverNewsAPI naverNewsAPI;
    
    // 클라이언트에서 'keyword' 파라미터로 검색어를 받아서 뉴스 정보 제공
    @GetMapping("/news/{keyword}")
    public Map<String, Object> getNews(@PathVariable String keyword) {
        System.out.println("백엔드 전달 완료 : " + keyword);
        JSONObject result = naverNewsAPI.getNews(keyword);
        System.out.println(result);
        
        // JSONObject를 Map으로 변환하여 반환
        Map<String, Object> resultMap = new HashMap<>();
        if (result != null) {
            resultMap.put("data", result.toMap());
        }
        return resultMap;
    }


}
