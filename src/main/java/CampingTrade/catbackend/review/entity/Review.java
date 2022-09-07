package CampingTrade.catbackend.review.entity;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.review.converter.StringListConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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

    @Convert(converter = StringListConverter.class)
    @Column(name = "images")
    private List<String> images = new ArrayList<>();

    public Review(String content, int rating, String createdDate, String modifiedDate, Long campingId,
                  Member writer, List<String> images) {
        this.content = content;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.campingId = campingId;
        this.writer = writer;
        this.images = images;
    }

}
