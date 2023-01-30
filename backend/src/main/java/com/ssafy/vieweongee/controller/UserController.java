//package com.ssafy.vieweongee.controller;
//
//import com.ssafy.vieweongee.entity.User;
//import com.ssafy.vieweongee.repository.UserRepository;
//import com.ssafy.vieweongee.service.Token;
//import com.ssafy.vieweongee.service.TokenService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RequiredArgsConstructor
//@RestController
//public class UserController {
//    private final PasswordEncoder passwordEncoder;
//    private final TokenService tokenService;
//    @Autowired
//    private final UserRepository userRepository;
//
//    @PostMapping("/normaljoin")
//    public ResponseEntity<?> join(@RequestBody User user){
////        return userRepository.save(User.builder()
////                .email(user.get("email"))
////                .password(passwordEncoder.encode(user.get("password")))
//////                .roles(Collections.singletonList("ROLE_USER"))
////                .build()).getId();
//        System.out.println("hi");
//        User dbUser = userRepository.findUserByEmail(user.getEmail());
//        if(dbUser == null){
//            userRepository.save(user);
//            return ResponseEntity.status(200).body("join success");
//        }
//        System.out.println("hi3");
//        return ResponseEntity.status(400).body("join fail");
//
//    }
//
//    @PostMapping("/normallogin")
//    public Token login(@RequestBody Map<String, String> user){
//        // 이메일로 회원 찾기.
//        User member=userRepository.findByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        if (!passwordEncoder.matches(user.get("password"), member.getPassword())){
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        return tokenService.generateToken(member.getUsername(), String.valueOf(member.getClass()));
//    }
//}
