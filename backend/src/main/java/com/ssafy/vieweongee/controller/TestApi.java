package com.ssafy.vieweongee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {
    @GetMapping("/test")
    public String index(){
        return "HEllo World!";
    }

    @GetMapping("https://nickname.hwanmoo.kr/?format=json&count=1&max_length=8")
    public String nickName(){
        return "";
    }
}
