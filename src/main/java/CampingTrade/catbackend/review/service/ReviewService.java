package CampingTrade.catbackend.review.service;

import CampingTrade.catbackend.common.handler.S3Uploader;
import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.dto.ReviewResponseDto;
import CampingTrade.catbackend.review.entity.Review;
import CampingTrade.catbackend.review.repository.ReviewQuerydslRepository;
import CampingTrade.catbackend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQuerydslRepository reviewQuerydslRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final S3Uploader s3Uploader;


    /* CREATE Review */
    @Transactional
    public void createReview(String token, Long campingId, ReviewRequestDto reviewRequestDto) throws IOException {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        List<MultipartFile> images = reviewRequestDto.getImages();

        Review review = Review.builder()
                .content(reviewRequestDto.getContent())
                .rating(reviewRequestDto.getRating())
                .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .campingId(campingId)
                .writer(member)
                .images(new ArrayList<>())
                .build();

        if (!images.isEmpty()) {
            images.forEach((f) -> {
                try {
                    String S3Url = s3Uploader.upload(f, "static");
                    review.getImages().add(S3Url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        reviewRepository.save(review);

    }

    /* UPDATE Review */
    @Transactional
    public void updateReview(String token, Long campingId, Long reviewId, ReviewRequestDto reviewRequestDto) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);


        if (member.getId() != reviewRepository.findById(reviewId).get().getWriter().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰 작성자가 아닙니다.");
        }

        Review review = reviewQuerydslRepository.findByCampingIdAndReviewId(campingId, reviewId);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.");
        }

       reviewQuerydslRepository.updateReview(reviewRequestDto.getContent(),
               reviewRequestDto.getRating(), campingId, reviewId);

    }


    /* DELETE Review */
    @Transactional
    public void deleteReview(String token, Long campingId, Long reviewId) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        if (member.getId() != reviewRepository.findById(reviewId).get().getWriter().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰 작성자가 아닙니다.");
        }

        Review review = reviewQuerydslRepository.findByCampingIdAndReviewId(campingId, reviewId);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.");
        }

        reviewRepository.delete(review);
    }

    /* 리뷰 목록 반환 */
    public List<ReviewResponseDto> getReviewList(Long campingId) {
        List<ReviewResponseDto> reviewList = reviewQuerydslRepository.findReviewListByCampingId(campingId);
        return reviewList;
    }



}
