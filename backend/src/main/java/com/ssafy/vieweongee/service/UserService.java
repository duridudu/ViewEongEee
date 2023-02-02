package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.User;

public interface UserService {
    User login(User user);
    User createUser(UserCreateRequest registInfo);

    Long getUserId(User user);
    User getUserByEmail(String email);
    User getUserById(Long id);
    boolean checkDuplicatedEmail(String email);
    boolean checkDuplicatedNickname(String nickname);

    String getJwtToken(Long id);

    void logout(Long id, String accessToken);
}
