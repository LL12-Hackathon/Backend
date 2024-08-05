// MemberDetails.java
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
public class MemberDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_details_id")
    private Long id;

    private String email;
    private LocalDateTime createdAt;
    private Integer receivedLikeCnt;
    private String company;
    private String department;
    private String position;
    private String career;

    @OneToMany(mappedBy = "memberDetails", orphanRemoval = true)
    private List<Board> boards;

    @OneToMany(mappedBy = "memberDetails", orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "memberDetails", orphanRemoval = true)
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // New field for loginId
    private String loginId;

    public void likeChange(Integer receivedLikeCnt) {
        this.receivedLikeCnt = receivedLikeCnt;
    }

    public void setMember(Member member) {
        this.member = member;
        if (member.getMemberDetails() != this) {
            member.setMemberDetails(this);
        }
    }
}