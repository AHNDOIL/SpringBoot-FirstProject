package com.example.firstproject.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") // 연결 url -> localhost:8080/hi
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "name");
        return "greetings"; //templates/greetings.mustache -> 브라우저 전송
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "DoIL");
        return "goodbye";
    }
}


