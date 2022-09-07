package CampingTrade.catbackend.review.dto;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.review.entity.Review;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {

    private String content;
    private int rating;
    private MultipartFile image;

}
