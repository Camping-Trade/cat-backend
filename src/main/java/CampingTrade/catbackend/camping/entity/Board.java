package CampingTrade.catbackend.camping.entity;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.review.entity.Attachment;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ"
)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime wrtieTime;
    private Boolean isDeleted;
    private String title;
    private String content;

    @Builder
    public Board(Member writer, LocalDateTime wrtieTime, Boolean isDeleted,
                 String title, String content) {
        this.writer = writer;
        this.wrtieTime = wrtieTime;
        this.isDeleted = isDeleted;
        this.title = title;
        this.content = content;
    }


}
