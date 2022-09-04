package CampingTrade.catbackend.camping.controller;

import CampingTrade.catbackend.camping.dto.CampingReviewResponse;
import CampingTrade.catbackend.common.dto.ApiResponse;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.review.component.FileStore;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.service.ReviewService;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/camping")
public class CampingController {

    private final FileStore fileStore;
    private final ReviewService reviewService;

    /* CREATE Review */
    @ApiOperation(value = "리뷰 작성", notes = "상세 페이지 - 캠핑장 리뷰 작성")
    @PostMapping("/details/{campingId}/reviews")
    public ResponseEntity<Void> createReview(@PathVariable Long campingId, HttpServletRequest request,
                                             @RequestBody ReviewRequestDto reviewRequestDto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.createReview(token, campingId, reviewRequestDto);
        return ApiResponse.success(null);
    }


    // UPDATE Review
    @ApiOperation(value = "리뷰 수정", notes = "상세 페이지 - 캠핑장 리뷰 수정")
    @PutMapping("/details/{campingId}/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long campingId, @PathVariable Long reviewId,
                                             HttpServletRequest request,
                                             @RequestBody ReviewRequestDto reviewRequestDto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.updateReview(token, campingId, reviewId, reviewRequestDto);
        return ApiResponse.success(null);
    }


    /* DELETE Review */
    @ApiOperation(value = "리뷰 삭제", notes = "상세 페이지 - 캠핑장 리뷰 삭제")
    @DeleteMapping("details/{campingId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long campingId, @PathVariable Long reviewId,
                                             HttpServletRequest request) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.deleteReview(token, campingId, reviewId);
        return ApiResponse.success(null);
    }


    // 리뷰 반환
    @GetMapping("/details/{campingId}/reviews")
    public ResponseEntity<CampingReviewResponse> getReviewList(@PathVariable Long campingId) {

        return null;

    }

    // 이미지 로드
    @GetMapping("/images/{filename}")
    public UrlResource processImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.createPath(filename));
    }

}
