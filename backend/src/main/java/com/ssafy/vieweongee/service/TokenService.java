package com.ssafy.vieweongee.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService{
    private String secretKey="webfirewood";
    private long tokenPeriod = 1000L * 60L * 10L;
//    private final User
    private long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

//    @Autowired
    private final UserDetailsService userDetailsService;
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() throws Exception {
//        return super.userDetailsService();
//    }
//    @Bean
    public TokenService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    public TokenService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token generateToken(String uid, String role){
        // JWT payload에 저장되는 정보 단위
        Claims claims= Jwts.claims().setSubject(uid);
        claims.put("role",role);
//        Map<String, Object> headers = new HashMap<>(); headers.put("typ", "JWT");
        Date now=new Date();
        return new Token(
                Jwts.builder()
//                        .setHeader(headers)
                        .setClaims(claims) // 정보 저장
                        .setIssuedAt(now) // 토큰 발행 시간 정보
                        .setExpiration(new Date(now.getTime()+tokenPeriod)) // 만료시간 설정
                        .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘
                        .compact(),
                Jwts.builder()
//                        .setHeader(headers)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }

    // JWT 토큰에서 인증정보 조회
    public Authentication getAuthenticatioin(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUid(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    // request 헤더에서 토큰 값 가져옴
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean verifyToken(String token){
        try{
            Jws<Claims> claims=Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        }catch(Exception e){
            return false;
        }
    }

    public String getUid(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
