package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.review.entity.Post;
import CampingTrade.catbackend.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {

    private Long reviewId;
    private String content;
    private int rating;

    private String createdDate = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    );
    private String modifiedate = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    );

    private Member member;
    private Post post;

    /* Dto -> Entity */
    public Review toEntity() {
        Review review = Review.builder()
                .reviewId(reviewId)
                .content(content)
                .rating(rating)
                .createdDate(createdDate)
                .modifiedDate(modifiedate)
                .member(member)
                .post(post)
                .build();

        return review;
    }
}
