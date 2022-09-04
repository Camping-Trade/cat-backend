package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewRequestDto {

    private String content;
    private int rating;
   //private List<MultipartFile> imageFiles = new ArrayList<>();


/*
    @Builder
    public ReviewRequestDto(String content, int rating, List<MultipartFile> imageFiles) {
        this.content = content;
        this.rating = rating;
        this.imageFiles = (imageFiles != null) ? imageFiles: new ArrayList<>();
    }

 */




    @Builder
    public ReviewRequestDto(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }



    /*
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
    private List<String> imagePathList = new ArrayList<>();

    // Dto -> Entity
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
    */
}
