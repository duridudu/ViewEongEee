package com.ssafy.vieweongee.controller;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController("/test")
public class SocialUserController {
//    @GetMapping("")
//    public String index(){
//        return "HEllo World!";
//    }

    @GetMapping("/nickname")
    public String nickName() throws Exception{
        String result=null;
        try{
            JSONObject reqParams = new JSONObject();
//            reqParams.put();

            URL url = new URL("https://nickname.hwanmoo.kr/?format=text&count=1&max_length=7");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            result=(String) in.readLine();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
