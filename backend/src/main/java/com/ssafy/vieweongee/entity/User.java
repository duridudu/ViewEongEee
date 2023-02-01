package com.ssafy.vieweongee.entity;

import com.ssafy.vieweongee.dto.user.request.SocialCreateRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.access.prepost.PreAuthorize;

//import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
@DynamicUpdate
@PreAuthorize("hasRole('USER')")
public class User{
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;
    @Column(length = 30,nullable = false)
    private String email;
//    @Column(nullable = false)
//    private String picture;
    @Column()
    private String password;

    @Transient
    private String passwordCheck;

    @Transient
    private String access_token;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(length = 10)
    private String social_login;

    private String social_token;

//    @Column(length = 10)
//    private String nickname;
    private String jwt_token;

    //    private ArrayList<> roleList;
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<Study> studies = new ArrayList<>();

    @OneToMany(mappedBy = "participant_id.user_id", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "progress_id.user_id", cascade = CascadeType.ALL)
    private List<Progress> progresses = new ArrayList<>();

    @OneToMany(mappedBy = "score_id.user_id", cascade = CascadeType.ALL)
    private List<Scorecard> scorecards = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public User(Long id, String name, String email, String password, String passwordCheck, String access_token, Role role, String social_login, String social_token, String jwt_token, List<Notice> notices, List<Alarm> alarms, List<Study> studies, List<Participant> participants, List<Progress> progresses, List<Scorecard> scorecards, List<Comment> comments, List<Reply> replies) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.access_token = access_token;
        this.role = role;
        this.social_login = social_login;
        this.social_token = social_token;
        this.jwt_token = jwt_token;
        this.notices = notices;
        this.alarms = alarms;
        this.studies = studies;
        this.participants = participants;
        this.progresses = progresses;
        this.scorecards = scorecards;
        this.comments = comments;
        this.replies = replies;
    }

    // 일반 로그인
    @Builder
    public User(String email, String name, String password, String passwordCheck) {

        this.email=email;
        this.name=name;
        this.password=password;
        this.passwordCheck=passwordCheck;
    }


    public User(UserCreateRequest registInfo) {
        this.email=registInfo.getEmail();
        this.name=registInfo.getName();
        this.password=registInfo.getPassword();
        this.passwordCheck=registInfo.getPasswordCheck();
        this.social_login="global";
    }

    @Builder
    // 소셜 로그인
    public User(SocialCreateRequest socialInfo) {
        this.name=name;
        this.email=email;
        this.social_login=social_login;
        this.jwt_token=jwt_token;
        this.access_token=access_token;
    }

    public User(String email, String name, String social_login) {
        this.name=name;
        this.email=email;
        this.social_login=social_login;
    }

    public void setJwtToken(String refreshJwt) {
        this.jwt_token=refreshJwt;
    }


}
