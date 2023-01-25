package com.ssafy.vieweongee.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findUserByName(String email);

//    boolean existsByEmail(String email);

    @Query("SELECT u.social_token FROM User u WHERE u.email=:email")
    String getRefreshTokenByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    List<User> getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email=:email and u.social_login=:social")
    User getUserByEmailandSocial(@Param("email") String email,@Param("social") String social);

    @Query("SELECT u FROM User u WHERE u.name=:email")
    Optional<User> getUserByName(@Param("email") String email);
    @Query("SELECT u.name FROM User u WHERE u.email=:email")
    String getNameByEmail(@Param("email") String email);

    @Query("SELECT u.picture FROM User u WHERE u.email=:email")
    String getPictureByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.social_token=:token WHERE u.email=:email")
    void updateRefreshToken(@Param("email") String email, @Param("token") String token);

//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.social_login=:socail_login WHERE u.email=:email")
//    void updateSocialName(@Param("email") String email, @Param("social") String social_login);
    User findUserByEmail(String email);


}