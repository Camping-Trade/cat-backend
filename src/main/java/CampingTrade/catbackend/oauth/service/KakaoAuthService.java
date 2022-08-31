package CampingTrade.catbackend.oauth.service;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.repository.MemberQuerydslRepository;
import CampingTrade.catbackend.member.repository.MemberRepository;
import CampingTrade.catbackend.oauth.client.ClientKakao;
import CampingTrade.catbackend.oauth.payload.AuthRequest;
import CampingTrade.catbackend.oauth.payload.AuthResponse;
import CampingTrade.catbackend.oauth.token.AuthToken;
import CampingTrade.catbackend.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final ClientKakao clientKakao;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {

        // userData 담기
        Member kakaoMember = clientKakao.getUserData(authRequest.getAccessToken());

        String kakaoId = kakaoMember.getKakaoId();
        Member member = memberQuerydslRepository.findByKakaoId(kakaoId);

        // 신규 토큰 생성
        AuthToken appToken = authTokenProvider.createUserAppToken(kakaoId);

        // 기존에 없는 사용자라면 새로 등록
        if (member == null) {
            memberRepository.save(kakaoMember);
            return AuthResponse.builder()
                    .appToken(appToken.getToken())
                    .isNewMember(Boolean.TRUE)
                    .build();
        }

        // /auth/kakao의 응답의 body로 AccessToken(여기선 appToken)을 보내주기 위해 builder 사용
        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }
}
