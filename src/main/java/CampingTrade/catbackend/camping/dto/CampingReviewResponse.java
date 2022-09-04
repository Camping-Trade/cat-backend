package CampingTrade.catbackend.camping.dto;

import CampingTrade.catbackend.review.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampingReviewResponse {

    private Long campingId;
    private List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();
}
