package CampingTrade.catbackend.oauth.client;

import CampingTrade.catbackend.api.entity.member.Member;
import CampingTrade.catbackend.oauth.entity.RoleType;
import CampingTrade.catbackend.oauth.exception.TokenValidFailedException;
import CampingTrade.catbackend.oauth.payload.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientKakao {
    private final WebClient webClient;

    // TODO ADMIN 유저 생성 시 getAdminUserData 메소드 생성 필요
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me") // Kakao의 유저 정보 받아오는 url
                .headers(h -> h.setBearerAuth(accessToken)) // JWT 토큰을 Bearer 토큰으로 지정
                .retrieve()
                // onStatus <- error handling
                .onStatus(HttpStatus::is4xxClientError, response
                        -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized.")))
                .onStatus(HttpStatus::is5xxServerError, response
                        -> Mono.error(new TokenValidFailedException("Internal Server Error.")))
                .bodyToMono(KakaoUserResponse.class) // Kakao의 유저 정보를 넣을 DTO 클래스
                .block();

        return Member.builder()
                .id(kakaoUserResponse.getId())
                .nickname(kakaoUserResponse.getProperties().getNickname())
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .roleType(RoleType.USER)
                .profileImageUrl(kakaoUserResponse.getProperties().getProfileImage())
                .build();
    }
}
