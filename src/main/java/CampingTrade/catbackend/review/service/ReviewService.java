package CampingTrade.catbackend.review.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.entity.Review;
import CampingTrade.catbackend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    /* CREATE */
    @Transactional
    public void createReview(String token, Long id, ReviewRequestDto dto) {
        Long memberId = authService.getMemberId(token);

        Member member = memberRepository.findMemberById(memberId);

        dto.setMember(member);

        Review review = dto.toEntity();
        reviewRepository.save(review);
    }

    /* UPDATE */
    @Transactional
    public void updateReview(String token, Long id, ReviewRequestDto dto) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        if (member.getId() != reviewRepository.findById(id).get().getMember().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰 작성자가 아닙니다.");
        }

        review.update(dto.getContent());
    }

    /* DELETE */
    @Transactional
    public void deleteReview(String token, Long id) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id = " + id));

        if (member.getId() != reviewRepository.findById(id).get().getMember().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰 작성자가 아닙니다.");
        }

        reviewRepository.delete(review);
    }
}
