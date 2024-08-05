// Like.java
package com.example.meetpro.domain.board;

import com.example.meetpro.domain.MemberDetails;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_details_id")
    private MemberDetails memberDetails;

    public static class LikeBuilder {
        // Ensure this method exists
        public LikeBuilder memberDetails(MemberDetails memberDetails) {
            this.memberDetails = memberDetails;
            return this;
        }
    }
}