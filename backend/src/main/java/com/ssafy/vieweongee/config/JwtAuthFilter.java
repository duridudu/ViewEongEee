package com.ssafy.vieweongee.config;

import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final TokenService tokenService;

//    private final UserRepository userRepository;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String token =((HttpServletRequest) request).getHeader("Auth");
//        String token=getJwt();
        String token=tokenService.resolveToken((HttpServletRequest) request);
//        String token="JWT";
        log.info("{}", token);
        // null 아니고 유효한 토큰인지 확인
        if (token != null && tokenService.verifyToken(token)) {
            Authentication authentication=tokenService.getAuthenticatioin(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String email = tokenService.getUid(token);
//            UserRepository userRepository = null;
//            User userDto = User.builder()
//                    .email(email)
//                    .name(userRepository.getNameByEmail(email))
//                    .picture(userRepository.getPictureByEmail(email)).build();
//            Authentication auth = getAuthentication(userDto);
//            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    private String getJwt() {
//        HttpServletRequest response=((ServletResponseAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();


        return request.getHeader("JWT");
    }

    public Authentication getAuthentication(User member){
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
