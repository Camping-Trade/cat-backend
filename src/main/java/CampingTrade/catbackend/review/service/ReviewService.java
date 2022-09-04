package CampingTrade.catbackend.review.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.entity.Attachment;
import CampingTrade.catbackend.review.entity.Review;
import CampingTrade.catbackend.review.repository.ReviewQuerydslRepository;
import CampingTrade.catbackend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQuerydslRepository reviewQuerydslRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final AttachmentService attachmentService;

    /* CREATE Review */
    @Transactional
    public void createReview(String token, Long campingId, ReviewRequestDto reviewRequestDto) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        reviewRepository.save(Review.builder()
                        .writer(member)
                        .content(reviewRequestDto.getContent())
                        .rating(reviewRequestDto.getRating())
                        .campingId(campingId)
                        .createdDate(LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                        .build());

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


    /*
    public Review post(ReviewRequestDto reviewRequestDto) throws IOException {
        List<Attachment> attachments = attachmentService.saveAttachments(reviewRequestDto.getAttachmentFiles());

        Review review = reviewRequestDto.createReview();
        attachments.stream()
                .forEach(attachment -> review.setAttachment(attachment));

        return reviewRepository.save(review);
    }

     */
}
