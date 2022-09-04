package CampingTrade.catbackend.review.entity;

import CampingTrade.catbackend.camping.entity.Board;
import CampingTrade.catbackend.member.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private int rating;


    @Column(name = "created_date")
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;


    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    //private LocalDateTime createdDate;

    /*
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
     */

    @Column(name = "camping_id")
    private Long campingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    /*
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Attachment> imageFiles = new ArrayList<>();
    */

    public void update(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    /*
    public void setAttachment(Attachment attachment) {
        this.imageFiles.add(attachment);
        attachment.setReview(this);
    }
     */
}
