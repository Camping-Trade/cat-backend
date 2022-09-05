package CampingTrade.catbackend.reservation.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import CampingTrade.catbackend.reservation.dto.ReservationRequestDto;
import CampingTrade.catbackend.reservation.entity.Reservation;
import CampingTrade.catbackend.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void createReservation(String token, Long campingId, ReservationRequestDto dto) {
        Long memberId = authService.getMemberId(token);
        Member member = memberRepository.findMemberById(memberId);

        reservationRepository.save(Reservation.builder()
                .member(member)
                .campingId(campingId)
                .campingName(dto.getCampingName())
                .locationX(dto.getLocationX())
                .locationY(dto.getLocationY())
                .campingDateStart(dto.getCampingDateStart())
                .campingDateEnd(dto.getCampingDateEnd())
                .reservationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .numberOfPeople(dto.getNumberOfPeople())
                .usingPoint(dto.getUsingPoint())
                .price(dto.getPrice())
                .build());
    }
}
