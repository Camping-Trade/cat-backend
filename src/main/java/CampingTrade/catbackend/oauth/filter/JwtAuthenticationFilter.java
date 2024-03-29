package CampingTrade.catbackend.oauth.filter;

import CampingTrade.catbackend.oauth.token.AuthToken;
import CampingTrade.catbackend.oauth.token.AuthTokenProvider;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization"); // Authorization 헤더 꺼냄

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // JWT 토큰 존재하는지 확인
            String tokenStr = JwtHeaderUtil.getAccessToken(request); // Bearer로 시작하는 값에서 Bearer를 제거한 accessToken(여기선 appToken) 반환

            AuthToken token = tokenProvider.createUserAppToken(tokenStr); //**

            if (token.validate()) { // token이 유효한지 확인
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication); // token에 존재하는 authentication 정보 삽입
            }
            
            filterChain.doFilter(request, response);
        }
    }
}
