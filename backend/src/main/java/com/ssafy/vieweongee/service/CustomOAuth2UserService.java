package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.controller.SocialUserController;
import com.ssafy.vieweongee.repository.StudyRepository;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        String email=null;
        String social = oAuth2Attribute.getSocial_login();

        log.info("지금 소셜 : {}", social);
        log.info("지금 oAuth2Attribute : {}", oAuth2Attribute);
        log.info("지금 유저 : {}",oAuth2User);

        if (social.equals("kakao")){
            log.info("나 들아왓어~~....");
//            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
//            log.info("카카오 어트리뷰투...{}", oAuth2User.getAttribute("kakao_account"));

            Map<String, Object>kakaoProfile = (Map<String, Object>)oAuth2User.getAttributes().get("kakao_account");
            Map<String, Object>kakaosAccount=(Map<String, Object>)kakaoProfile.get("profile");

            log.info("카카오 어카운트... {}", kakaosAccount);

            email= kakaoProfile.get("email").toString();
            log.info("카카오 이메일은~... {}",email);
            List<User> findMember = userRepository.getUserByEmail(email);

            User user;
            try{
                if (findMember==null)  {
                    log.info("카카오 로그인이 처음! 회원으로 등록");
                    log.info("{}", oAuth2Attribute.getAttributeKey());
                    user = new User(email, kakaosAccount.get("nickname").toString(), SocialUserController.NickName(), kakaosAccount.get("profile_image_url").toString(), oAuth2Attribute.getSocial_login());
                    userRepository.save(user);
                }
                else {
                    user=userRepository.getUserByEmailandSocial(email, social);
                    if (user==null){
                        log.info("카카오 로그인이 처음! 회원으로 등록");
                        log.info("{}", oAuth2Attribute.getAttributeKey());
                        user = new User(email, kakaosAccount.get("nickname").toString(), SocialUserController.NickName(), kakaosAccount.get("profile_image_url").toString(), oAuth2Attribute.getSocial_login());
                        userRepository.save(user);
                    }
                        log.info("카카오 로그인 기록이 있습니당");
//                user=userRepository.findUserByEmail(email);

                }
            }catch (RuntimeException e){
                throw new RuntimeException();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (social.equals("naver")){
            email=oAuth2Attribute.getEmail();
            List<User> findMember = userRepository.getUserByEmail(email);
            log.info("find memeber : {}", findMember);
            User user;
            try{
                if (findMember==null) {
                    log.info("로그인이 처음! 회원으로 등록");
                    log.info("{}", oAuth2Attribute.getAttributeKey());
                    user = new User(oAuth2User.getAttribute("email"), oAuth2User.getAttribute("name"),SocialUserController.NickName(), oAuth2User.getAttribute("picture"), oAuth2Attribute.getSocial_login());
                    userRepository.save(user);
                }
                else{
                    user=userRepository.getUserByEmailandSocial(email,social);
                    log.info("나 네이버야.......{}", user);
                    if (user==null){
                        log.info("로그인이 처음! 회원으로 등록");
                        String nickname=SocialUserController.NickName();
                        log.info("{}", oAuth2Attribute.getAttributeKey());
                        log.info("서비스 패키지에서의 닉네임은?!!!! {}",nickname );
                        user = new User(email, oAuth2Attribute.getName(), nickname,oAuth2Attribute.getPicture(), oAuth2Attribute.getSocial_login());
                        userRepository.save(user);

                    }
                    log.info("로그인 기록이 있습니당");
                }
            }catch (Exception e){
                throw new RuntimeException();
            }
        }else{
                email=oAuth2Attribute.getEmail();
                log.info("email : {}", email);
                List<User> findMember = userRepository.getUserByEmail(email);

                User user;
                try{
                    if (findMember==null) {
                        log.info("로그인이 처음! 회원으로 등록");
                        log.info("{}", oAuth2Attribute.getAttributeKey());
                        user = new User(oAuth2User.getAttribute("email"), oAuth2User.getAttribute("name"), SocialUserController.NickName(),oAuth2User.getAttribute("picture"), oAuth2Attribute.getSocial_login());
                        userRepository.save(user);
                    }
                    else{
                        user=userRepository.getUserByEmailandSocial(email,social);
                        log.info("나 구글이야.......{}", user);
                        if (user==null){
                            log.info("로그인이 처음! 회원으로 등록");
                            log.info("{}", oAuth2Attribute.getAttributeKey());
                            user = new User(oAuth2User.getAttribute("email"), oAuth2User.getAttribute("name"), SocialUserController.NickName(),oAuth2User.getAttribute("picture"), oAuth2Attribute.getSocial_login());
                            userRepository.save(user);
                        }
                        log.info("로그인 기록이 있습니당");
                    }
                }
                catch (Exception e){
                    throw new RuntimeException();
                }
            }


        var memberAttribute = oAuth2Attribute.convertToMap();
        log.info("member Attrubute : {}", memberAttribute);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                memberAttribute, "email");
    }


//    private User saveOrUpdate(OAuth2Attribute oAuth2Attribute) {
////        Optional<User> findMember = userRepository.findByEmail(oAuth2Attribute.getEmail());
////        if (findMember.isPresent()){
////            User user=findMember.get();
////            user.updateRefreshToken(jwtTokenPri)
////
////        }
//        User user= (User) userRepository.findByEmail(oAuth2Attribute.getEmail())
//                .orElse((User) UserProfile.toUser(oAuth2Attribute.getName(),oAuth2Attribute.getEmail(),oAuth2Attribute.getPicture()));
//        return userRepository.save(user);
//    }
}
