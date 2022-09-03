package CampingTrade.catbackend.review.controller;

import CampingTrade.catbackend.common.payload.ApiResponse;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.service.ReviewService;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/camping")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewService reviewService;

    /* CREATE */
    @ApiOperation(value = "리뷰 작성", notes = "캠핑장 리뷰 작성 - 상세페이지")
    @PostMapping("/details/{id}/reviews")
    public ResponseEntity<Void> createReview(HttpServletRequest request, @PathVariable Long id,
                                             @RequestBody ReviewRequestDto dto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.createReview(token, id, dto);
        return ApiResponse.success(null);
    }

    /* UPDATE */
    @ApiOperation(value = "리뷰 수정", notes = "캠핑장 리뷰 수정 - 상세페이지")
    @PutMapping({"/details/{id}/reviews/{id}"})
    public ResponseEntity<Void> updateReview(HttpServletRequest request, @PathVariable Long id,
                                             @RequestBody ReviewRequestDto dto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.updateReview(token, id, dto);
        return ApiResponse.success(null);
    }

    /* DELETE */
    @ApiOperation(value = "리뷰 삭제", notes = "캠핑장 리뷰 삭제 - 상세페이지")
    @DeleteMapping("/details/{id}/reviews/{id}")
    public ResponseEntity<Void> deleteReview(HttpServletRequest request, @PathVariable Long id) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.deleteReview(token, id);
        return ApiResponse.success(null);
    }
}
