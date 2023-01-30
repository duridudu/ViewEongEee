package com.ssafy.vieweongee.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
@DynamicUpdate
public class User implements UserDetails{
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;
    @Column(length = 30,nullable = false)
    private String email;
    @Column(nullable = false)
    private String picture;
    @Column(length = 16)
    private String password;

    @Column(length = 10)
    private String social_login;

    private String social_token;

    @Column(length = 10)
    private String nickname;
    private String jwt_token;


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
    public User(List<String> roles, Long id, String name, String picture,String email, String password, String social_login, String social_token, String nickname, String jwt_token, List<Notice> notices, List<Alarm> alarms, List<Study> studies, List<Participant> participants, List<Progress> progresses, List<Scorecard> scorecards, List<Comment> comments, List<Reply> replies) {
        this.roles=roles;
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture=picture;
        this.password = password;
        this.social_login = social_login;
        this.social_token = social_token;
        this.nickname = nickname;
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

    public User(String email, String name, String nickname, String picture, String social_login) {
        this.email = email;
        this.name = name;
        this.nickname=nickname;
        this.picture = picture;
        this.social_login=social_login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
