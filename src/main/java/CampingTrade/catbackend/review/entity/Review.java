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
public class Review extends TimeEntity {

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

    @Column(name = "camping_id")
    private Long campingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Attachment> imageFiles = new ArrayList<>();


    @Builder
    public Review (Member writer, String content, int rating, String createdDate, String modifiedDate,
                   Long campingId) {
        this.writer = writer;
        this.content = content;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.campingId = campingId;
        //this.imageFiles = imageFiles;
    }


    public void setAttachment(Attachment attachment) {
        this.imageFiles.add(attachment);
        attachment.setReview(this);
    }

}
