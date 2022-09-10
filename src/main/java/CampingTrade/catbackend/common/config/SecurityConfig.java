package CampingTrade.catbackend.common.config;


import CampingTrade.catbackend.oauth.filter.JwtAuthenticationFilter;
import CampingTrade.catbackend.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type=
        ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

    private final AuthTokenProvider authTokenProvider;

    // 암호화에 필요한 PasswordEncoder를 Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    // authenticationManager를 Bean 등록
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return authenticationManagerBean();
//    }

    // swagger-ui.html의 경우, 인증된 사용자가 아니어도 접근 가능하도록 설정
    // (dev 환경에 대해서만 swagger 설정을 했기 때문에 인증된 사용자가 아니어도 됨)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().mvcMatchers(
                "/v2/api-docs",
                "/configuration/**",
                "/swagger*/**",
                "/webjars/**"
        );
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authTokenProvider);

        http
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers(HttpMethod.OPTIONS).permitAll() // preflight 대응
                .antMatchers("/auth/**").permitAll() // "/auth/**"에 대한 접근을 인증 절차 없이 허용(로그인 관련 url)
                /*
                특정 권한을 가진 사용자만 접근을 허용해야 할 경우 -> 허가 항목을 통해 가능함
                ex) .antMatchers("/admin/**").hasAnyRole("ADMIN")
                 */
                .anyRequest().authenticated() // 위에서 따로 지정한 접근허용 리소스 설정 후, 그 외 나머지 리소스들은 무조건 인증 완료해야 접근 가능
                .and()
                .headers() // 아래에 X-Frame-Option 헤더 설정을 위해 headers() 작성
                .frameOptions().sameOrigin() // 동일 도메인에서는 iframe 접근 가능하도록 X-Frame-Options을 sameOrigin()으로
                .and()
                .cors()
                .and()
                .csrf().disable() // csrf 보안 토큰 disable 처리
                /*
                예외 처리를 하고 싶다면 다음과 같이 작성
                .exceptionHandling() // 예외 처리 지정
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .acceessDeniedHandler(new CustomAccessDeniedHander())
                 */
                //.and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증 -> 세선도 사용 X
                .and()
                // 커스텀 필터 등록해 기존에 지정된 필터에 앞서 시행
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
