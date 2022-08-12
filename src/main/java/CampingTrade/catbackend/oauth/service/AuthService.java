package CampingTrade.catbackend.oauth.service;

import CampingTrade.catbackend.oauth.payload.AuthResponse;
import CampingTrade.catbackend.oauth.token.AuthToken;
import CampingTrade.catbackend.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenProvider authTokenProvider;

    public AuthResponse updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();

        if (claims == null)
            return null;

        String memberId = claims.getSubject();

        AuthToken updatedAppToken = authTokenProvider.createUserAppToken(memberId);

        return AuthResponse.builder()
                .appToken(updatedAppToken.getToken())
                .build();
    }
}
