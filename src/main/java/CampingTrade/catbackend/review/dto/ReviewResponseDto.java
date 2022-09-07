package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long reviewId;
    private String content;
    private int rating;


    private String createdDate = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    );
    private String modifiedDate = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    );

    private String writer;
    private Long campingId;
    private List<String> images = new ArrayList<>();

    /* Entity -> Dto */
    public ReviewResponseDto (Review review) {
        this.reviewId = review.getReviewId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdDate = review.getCreatedDate();
        this.modifiedDate = review.getModifiedDate();
        this.writer = review.getWriter().getNickname();
        this.campingId = review.getCampingId();
    }
}
