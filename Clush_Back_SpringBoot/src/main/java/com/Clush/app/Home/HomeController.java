package com.Clush.app.Home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/clushAPI")
    public String home() {
        return "index"; // templates/index.html 반환
    }
}
