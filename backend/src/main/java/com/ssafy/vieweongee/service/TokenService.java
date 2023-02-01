package com.ssafy.vieweongee.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import com.ssafy.vieweongee.model.JwtProperties;
import com.ssafy.vieweongee.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
    private static String secretKey="c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
    private final UserRepository userRepository;

//    public TokenService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public static String createAccessToken(Long id){
        log.info("id : {}, username(id) : {}",id);
        return JWT.create()
                .withSubject(JwtProperties.ACCESS_TOKEN)
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim(JwtProperties.Id,id)
//                .withClaim(JwtProperties.USERNAME,username)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public static String createRefreshToken(){
        return JWT.create()
                .withSubject(JwtProperties.REFRESH_TOKEN)
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void checkHeaderValid(HttpServletRequest request, HttpServletResponse response){
        String accessJwt=request.getHeader(JwtProperties.HEADER_PREFIX);
        String refreshJwt=request.getHeader(JwtProperties.REFRESH_HEADER_PREFIX);

        if (accessJwt==null){
            throw new RuntimeException();
        }else if (refreshJwt==null){
            throw new RuntimeException();
        }
    }

    public boolean checkTokenValid(String token){
        JWTVerifier verifier =
            JWT.require(Algorithm.HMAC512(secretKey))
                .build();
        // 만료됐으면 에러남
        String username= verifier.verify(token).getClaim("USERNAME").toString();
        // 유저네임이 널 아니면 = 잘 검증돼서 유저네임 뽑히면 트루 반환
        if (username!=null){
            return true;
        }else{
            return false;
        }
    }

    public boolean isExpiredToken(String token){
        try{
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        }catch(TokenExpiredException e){
            log.info("만료된 토큰");
            return true;
        }
        return false;
    }
    @Transactional
    public void setRefreshToken(Long Id, String refreshJwt){
        log.info("여기는 serRefresh : {} / {}", Id,refreshJwt );
        userRepository.findById(Id)
                .ifPresent(user->user.setJwtToken(refreshJwt));
    }

    @Transactional
    public void removeRefreshToken(String token){
        userRepository.findByJwtToken(token)
                .ifPresent(m->m.setJwtToken(null));
    }

    public boolean isNeedToUpdateRefreshToken(String token){
        try{
            Date expiresAt = JWT.require(Algorithm.HMAC512((secretKey)))
                    .build()
                    .verify(token)
                    .getExpiresAt();
            Date current=new Date(System.currentTimeMillis());
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 7);

            Date after7dayFromToday=calendar.getTime();
            if(expiresAt.before(after7dayFromToday)){
                log.info("7일 이내에 만료!!");
                return true;
            }
        }catch (TokenExpiredException e){
            return true;
        }
        return false;
    }


//    public boolean verifyToken(String token){
//        try{
//            Jws<Claims> claims=Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//            return claims.getBody()
//                    .getExpiration()
//                    .after(new Date());
//        }catch(Exception e){
//            return false;
//        }
//    }
//
//    public String getUid(String token){
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }
}
