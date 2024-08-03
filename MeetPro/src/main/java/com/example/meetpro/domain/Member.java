package com.example.meetpro.domain;

import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.Comment;
import com.example.meetpro.domain.board.Like;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String loginId;
    private String password;

    //사용자 이름
    private String name;

    //사용자 이메일
    private String email;

    //사용자 닉네임
    private String nickname;
    private LocalDateTime createdAt;    // 가입 시간
    private Integer receivedLikeCnt; // 유저가 받은 좋아요 개수 (본인 제외)
    //사용자 회사명
    private String company;
    //사용자 부서
    private String department;
    //사용자 직책
    private String position;
    //사용자 경력
    private String career;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // provider : google이 들어감
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Board> boards;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Like> likes;       // 유저가 누른 좋아요

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Comment> comments;

    public void likeChange(Integer receivedLikeCnt) {
        this.receivedLikeCnt = receivedLikeCnt;
    }

    public void edit(String newPassword, String newNickname) {
        this.password = newPassword;
        this.nickname = newNickname;
    }
}
