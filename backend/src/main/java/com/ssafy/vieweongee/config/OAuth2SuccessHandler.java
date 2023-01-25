package com.ssafy.vieweongee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.entity.UserRepository;
import com.ssafy.vieweongee.model.UserRequestMapper;
import com.ssafy.vieweongee.service.Token;
import com.ssafy.vieweongee.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private  final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("oAuth2User : {}", oAuth2User.getAttributes());
        User userDto=userRequestMapper.toDto(oAuth2User);

//        User user=saveOrUpdate(userDto);
//        Optional<User> findMember = userRepository.findByEmail(userDto.getEmail());
//        if (findMember.isEmpty()) {
//            User user = findMember.get();
//        }
        var attributes = oAuth2User.getAttributes();
//        String email=userDto.getEmail();
        String email=(String)attributes.get("email");
        Token token =tokenService.generateToken(email,"USER");
        log.info("email in SuccessHandler : {}", email);
        userRepository.updateRefreshToken(email, (String)token.getRefreshToken());
//        userRepository.updateSocialName(email, (String))
        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        log.info("{}", token.getRefreshToken());
        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer=response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
