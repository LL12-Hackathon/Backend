package com.example.meetpro.domain.board;

import com.example.meetpro.domain.BaseEntity;
import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.MemberDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;      // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_details_id")
    private MemberDetails memberDetails; // 작성자 상세 정보

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;    // 댓글이 달린 게시판

    public void update(String newBody) {
        this.body = newBody;
    }
}