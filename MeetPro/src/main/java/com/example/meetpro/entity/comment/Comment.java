package com.example.meetpro.entity.comment;


import com.example.meetpro.entity.user.EntityDate;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import com.example.meetpro.entity.user.User;
import com.example.meetpro.entity.board.Board;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Comment extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    public Comment(final String content, final User user, final Board board) {
        this.content = content;
        this.user = user;
        this.board = board;
    }

    public boolean isOwnComment(final User user) {
        return this.user.equals(user);
    }
}
