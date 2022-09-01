package CampingTrade.catbackend.member.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.payload.MemberResponse;
import CampingTrade.catbackend.member.repository.MemberQuerydslRepository;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthService authService;
    private final MemberQuerydslRepository memberQuerydslRepository;

    public MemberResponse getMemberData (String token) {
        Long memberId = authService.getMemberId(token);
        MemberResponse memberResponse = memberQuerydslRepository.findByMemberId(memberId);

        if (memberResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }

        return memberResponse;
    }
}
