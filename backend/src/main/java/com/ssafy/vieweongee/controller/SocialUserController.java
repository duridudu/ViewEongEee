package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//@RestController("/test")
@Slf4j
public class SocialUserController {

    // 닉네임 한 개 불러오기
    public static String NickName() throws Exception{
        String result=null;
        try{
            URL url = new URL("https://nickname.hwanmoo.kr/?format=text&count=1&max_length=8");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/text; utf-8");
            conn.setDoOutput(true);

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            result=sb.toString();
            result = checkNickname(result);
            log.info("닉네임은!!!!!!!! {}", result);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 중복검사 후 확정
    public static String checkNickname(String nickname) throws Exception {
        String fin=null;
        UserRepository userRepository = null;

        // 중복되는 경우 true -> false가 나와야 함.
        // 중복이면 계쏙 새로 만들어서 fin에 저장
        while (userRepository.existsByNickname(nickname)){
            fin = NickName();
        }
        return fin;
    }


}
