package CampingTrade.catbackend.camping.controller;

import CampingTrade.catbackend.common.dto.ApiResponse;
import CampingTrade.catbackend.common.handler.S3Uploader;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.dto.ReviewResponseDto;
import CampingTrade.catbackend.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/camping")
public class CampingController {

    private final ReviewService reviewService;
    private final S3Uploader s3Uploader;

    /* CREATE Review */
    @ApiOperation(value = "리뷰 작성", notes = "상세 페이지 - 캠핑장 리뷰 작성")
    @PostMapping(path = "/details/{campingId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createReview(HttpServletRequest request,
                                             @PathVariable Long campingId,
                                             @RequestPart(required = false) List<MultipartFile> images,
                                             @RequestPart ReviewRequestDto reviewRequestDto)
            throws IOException {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewRequestDto.setImages(images);

        if (images != null) {
            reviewRequestDto.setImages(images);
        }
        else {
            reviewRequestDto.setImages(null);
        }

        reviewService.createReview(token, campingId, reviewRequestDto);
        return ApiResponse.success(null);
    }


    /* UPDATE Review */
    @ApiOperation(value = "리뷰 수정", notes = "상세 페이지 - 캠핑장 리뷰 수정")
    @PutMapping("/details/{campingId}/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(HttpServletRequest request,
                                             @PathVariable Long campingId, @PathVariable Long reviewId,
                                             @RequestBody ReviewRequestDto reviewRequestDto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.updateReview(token, campingId, reviewId, reviewRequestDto);
        return ApiResponse.success(null);
    }


    /* DELETE Review */
    @ApiOperation(value = "리뷰 삭제", notes = "상세 페이지 - 캠핑장 리뷰 삭제")
    @DeleteMapping("details/{campingId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(HttpServletRequest request,
                                             @PathVariable Long campingId, @PathVariable Long reviewId) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reviewService.deleteReview(token, campingId, reviewId);
        return ApiResponse.success(null);
    }


    /* Get Review list */
    @GetMapping("/details/{campingId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviewList(@PathVariable Long campingId) {

        return ApiResponse.success(reviewService.getReviewList(campingId));

    }

}
