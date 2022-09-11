package CampingTrade.catbackend.member.service;

import CampingTrade.catbackend.member.dto.MemberResponse;
import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberQuerydslRepository;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.reservation.dto.ReservationResponseDto;
import CampingTrade.catbackend.reservation.repository.ReservationQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthService authService;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final ReservationQuerydslRepository reservationQuerydslRepository;
    private final MemberRepository memberRepository;

    /* 유저 정보 반환 */
    public MemberResponse getMemberData(String token) {
        Long memberId = authService.getMemberId(token);
        MemberResponse memberResponse = memberQuerydslRepository.findByMemberId(memberId);

        if (memberResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }

        return memberResponse;
    }

    /* 예약 리스트 반환 */
    public List<ReservationResponseDto> getReservationData(String token) {
        Long memberId = authService.getMemberId(token);
        List<ReservationResponseDto> reservationList = reservationQuerydslRepository
                .findReservationListByMemberId(memberId);

        return reservationList;
    }

    //@Transactional
    /* 유저 정보 삭제
    public void deleteMember(String token) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        if (memberRepository.findMemberById(memberId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가입한 유저가 아닙니다.");
        }

        memberRepository.delete(member);
    }
     */
}
