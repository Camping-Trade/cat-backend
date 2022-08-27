package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String modifiedate = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    );

    private String nickname;
    private Long postId;

    /* Entity -> Dto */
    public ReviewResponseDto (Review review) {
        this.reviewId = review.getReviewId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdDate = review.getCreatedDate();
        this.modifiedate = review.getModifiedDate();
        this.nickname = review.getMember().getNickname();
        this.postId = review.getPost().getPostId();
    }
}
