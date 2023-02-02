package com.ssafy.vieweongee.controller;

import com.nimbusds.oauth2.sdk.Message;
import com.ssafy.vieweongee.dto.user.request.UserCheckRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.TokenService;
import com.ssafy.vieweongee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    //로그인
    @ResponseBody
    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody User user) {
        log.info("나 로그인하러 왔옹 : {}", user.getEmail());

        log.info("여긴 유저 컨트롤러.. 유저 이메일은 {} 유저 프로바이더는 : {}", user.getEmail(), "global");

        User loginUser = userService.login(user);
        if(loginUser != null){
            Long userId = userService.getUserId(user);
            String accessToken=tokenService.createAccessToken(userId);
            String refreshToken=tokenService.createRefreshToken();

            log.info("리프레쉬 앤 엑세스 : {} // {}", refreshToken, accessToken);
            tokenService.setRefreshToken(userId,refreshToken);

//            List<String> tokens=new ArrayList<>();

//            tokens.add(accessToken);
//            tokens.add(refreshToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add("ACCESS", accessToken);
            headers.add("REFRESH", refreshToken);
//           String message="SUCCESS";
//           response.addHeader("ACCESS", accessToken);
//           response.addHeader("REFRESH", refreshToken);

            return ResponseEntity.ok().headers(headers).body("SUCCESS");
        }
        return ResponseEntity.status(400).body("login fail");
    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest registInfo) {
        try {
            //email 중복 검사, email 인증번호 보내기

            //비밀번호 확인
            if(!registInfo.getPassword().equals(registInfo.getPasswordCheck()))
                return ResponseEntity.status(409).body("비밀번호가 일치하지 않습니다.");

            //닉네임 중복검사
            if(userService.checkDuplicatedNickname(registInfo.getName()))
                return ResponseEntity.status(409).body("nickname이 중복됩니다.");
//            registInfo.setRole("ROLE_USER");
            //회원 가입
            User user = userService.createUser(registInfo);
            return ResponseEntity.status(200).body("회원가입 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("회원가입 실패");
        }
    }

    //email 중복 검사
    @GetMapping("/email-check")
    public ResponseEntity<?> emailCheck(@RequestBody String email){
        if(!userService.checkDuplicatedEmail(email))
            return ResponseEntity.status(200).body("사용 가능한 이메일 입니다!");
        return ResponseEntity.status(409).body("이메일이 중복됩니다. 다른 이메일을 선택해 주세요");
    }

    // 액세스 토큰 확인
    @PostMapping("/check-access")
    public ResponseEntity checkAccess(@RequestBody UserCheckRequest userToken, HttpServletResponse response){
//        Map<String, Object> resultMap=new HashMap<>();
        // access 토큰을 확인
        boolean check=tokenService.checkTokenValid(userToken.getAccessToken());
        if (check==true){
            // 성공 응답
//            return ResponseEntity.status(200).body("access token이 만료돼지 않았습니당 패쓰!");
            return ResponseEntity.status(200).body("접근 허가");
        }
        // else라면 토큰 만료된거
        // refresh 토큰 달라고 요청 보내야함!!!
        return ResponseEntity.status(409).body("FAIL");
        // 원래 리프레쉬 토큰도 검증해야...ㄷㄷ
    }


    @PostMapping("/check-refresh")
    public ResponseEntity checkRefresh(@RequestBody UserCheckRequest userToken, HttpServletRequest response){
        log.info("리프레쉬 오긴 하나 ---> {}", userToken.getRefreshToken());
        log.info("DB의 리프레쉬 : {}", userService.getJwtToken(userToken.getId()));
        if (userToken.getRefreshToken().equals(userService.getJwtToken(userToken.getId()))){
            // 리프레쉬 토큰이 db와 일치하면 access 토큰 재발급해줄거야
            log.info("access 재발급 : {}, {}", userToken.getRefreshToken(), userToken.getId());
            String newAccessJwt=tokenService.createAccessToken(userToken.getId());
            HttpHeaders headers=new HttpHeaders();
            headers.add("ACCESS",newAccessJwt);


            return ResponseEntity.ok().headers(headers).body("NEW ACCESS TOKEN");
//            return ResponseEntity.status(200).body("new access token 발급");
        }
        else {
            // 리프레쉬 토큰이 db와 일치하지 않으면 요청 종료
            return ResponseEntity.status(409).body("요청 실패 - 로그인 검증 실패");
//            return ResponseEntity.status(409).body("요청 실패 - 로그인 검증 실패");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity logout(@RequestBody UserCheckRequest userToken, HttpServletResponse response){
        String accessToken=userToken.getAccessToken();
        boolean check=tokenService.checkTokenValid(userToken.getAccessToken());
        if (check==true){
            Long id=userToken.getId();
            userService.logout(id, accessToken);
            return ResponseEntity.status(200).body("로그아웃 완료");
        }
        return ResponseEntity.status(409).body("로그아웃 실패 - 토큰 만료");
    }

    //email 인증
//    @PostMapping("/email-valid")
//    public ResponseEntity<?> sendEmail(){
//
//    }

//    @PostMapping("/email-valid/certi")
//    public ResponseEntity<?> emailValid(){
//
//    }

    //닉네임 중복 검사
//    @GetMapping("/nickname-check")
//    public ResponseEntity<?> nicknameCheck(@RequestBody String nickname){
//        if(!userService.checkDuplicatedNickname(nickname))
//            return ResponseEntity.status(200).body("nickname is not duplicated");
//        return ResponseEntity.status(409).body("nickname is duplicated");
//    }

    //refresh token 재발급

    //비밀번호 찾기

    //

}
