package CampingTrade.catbackend.review.controller;

import CampingTrade.catbackend.common.payload.ApiResponse;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/camping")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 작성", notes = "캠핑장 리뷰 작성 - 상세페이지")
    @PostMapping("/details/{id}/reviews")
    public ResponseEntity<Void> createReview(HttpServletRequest request, @PathVariable Long id,
                                             @RequestBody ReviewRequestDto dto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.createReview(token, id, dto);
        return ApiResponse.success(null);
    }
}
