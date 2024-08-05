// Member.java
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
    //사용자 닉네임
    private String nickname;
    //사용자 이름
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // provider : google이 들어감
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
    private MemberDetails memberDetails;

    public void edit(String newPassword) {
        this.password = newPassword;
    }


    public void likeChange(Integer receivedLikeCnt) {
        if (memberDetails != null) {
            memberDetails.likeChange(receivedLikeCnt);
        }
    }

    public Integer getReceivedLikeCnt() {
        return memberDetails != null ? memberDetails.getReceivedLikeCnt() : null;
    }

    public void setMemberDetails(MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
        if (memberDetails.getMember() != this) {
            memberDetails.setMember(this);
        }
    }
}