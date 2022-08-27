package CampingTrade.catbackend.review.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.review.dto.ReviewRequestDto;
import CampingTrade.catbackend.review.entity.Review;
import CampingTrade.catbackend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    /* 리뷰 작성 */
    @Transactional
    public void createReview(String token, Long id, ReviewRequestDto dto) {

        Long memberId = authService.getMemberId(token);

        Member member = memberRepository.findMemberById(memberId);

        dto.setMember(member);

        Review review = dto.toEntity();
        reviewRepository.save(review);

    }

}
