package com.ssafy.vieweongee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vieweongee.repository.UserRepository;
import com.ssafy.vieweongee.service.CustomOAuth2UserService;
import com.ssafy.vieweongee.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserRepository userRepository;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeRequests()
//                .antMatchers("api/users/**").permitAll()
//                .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()					//추가
                .oauth2Login().successHandler(successHandler)			// OAuth2기반의 로그인인 경우
//                .loginPage("")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
//                .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
//                .failureUrl("/loginForm")		// 로그인 실패 시 /loginForm으로 이동
                .userInfoEndpoint()			// 로그인 성공 후 사용자정보를 가져온다
                .userService(oAuth2UserService);	//사용자정보를 처리할 때 사용한다

//                .addFilter(new JwtAuthenticationFilter(authenticationManager(),new TokenService(userRepository)))
//                .anonymous()
//                    .oauth2Login().authorizeRequests()
//                    .successHandler(successHandler)
//                    .userInfoEndpoint().userService(oAuth2UserService)
//
//                .antMatchers().permitAll();



//                .authorizeRequests()
//                .antMatchers("/api/users/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin();

//                .loginPage()
//                .loginProcessingUrl("loginProc")
//                .defaultSuccessUrl("/")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true);
    }

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
