package CampingTrade.catbackend.oauth.token;

import CampingTrade.catbackend.oauth.enumerate.RoleType;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String socialId, RoleType roleType, Date expiry, Key key) {
        String role = roleType.toString(); // USER, ADMIN
        this.key = key;
        this.token = createAuthToken(socialId, role, expiry);
    }

    // AccessToken(여기선 appToken) 생성
    private String createAuthToken(String memberId, String role, Date expiry) {
        return Jwts.builder()
                .setSubject(memberId)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    // AccessToken(여기선 appToken) 유효한지 체크
    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // token의 Body가 다음의 exception들로 인해 유효하지 않으면 각각의 로그를 콘솔에 출력

        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            // 처음 로그인(/auth/kakao) 할 때, AccessToken(여기선 appToken) 없이 접근해도 token validate 체크
            // -> exception 터트리지 않고 catch로 잡아줌
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }
}
