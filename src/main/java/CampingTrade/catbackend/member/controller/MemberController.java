package CampingTrade.catbackend.member.controller;

import CampingTrade.catbackend.common.dto.ApiResponse;
import CampingTrade.catbackend.member.dto.MemberResponse;
import CampingTrade.catbackend.member.service.MemberService;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.reservation.dto.ReservationResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://camping-trade.netlify.app")
public class MemberController {

    private final MemberService memberService;

    /* 유저 - 개인 정보 조회 */
    @ApiOperation(value = "사용자 정보 조회", notes = "카카오 로그인을 통해 저장한 사용자의 정보 반환")
    @GetMapping("/details")
    public ResponseEntity<MemberResponse> getMemberData(HttpServletRequest request) {
        String token = JwtHeaderUtil.getAccessToken(request);
        return ApiResponse.success(memberService.getMemberData(token));
    }

    /* 유저 - 예약 정보 조회 */
    @ApiOperation(value = "예약 정보 조회", notes = "사용자의 예약 내역 반환")
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservationData(HttpServletRequest request) {
        String token = JwtHeaderUtil.getAccessToken(request);
        return ApiResponse.success(memberService.getReservationData(token));
    }

    /* 유저 - 나눔 정보 조회 */

    /* 유저 - 회원 탈퇴
    @ApiOperation(value = "회원 탈퇴", notes = "사용자의 회원 탈퇴 및 정보 삭제")
    @DeleteMapping("/deleted")
    public ResponseEntity<Void> deleteMember(HttpServletRequest request) {
        String token = JwtHeaderUtil.getAccessToken(request);
        memberService.deleteMember(token);
        return ApiResponse.success(null);
    }
     */
}
