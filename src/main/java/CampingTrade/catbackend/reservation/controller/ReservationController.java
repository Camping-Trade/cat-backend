package CampingTrade.catbackend.reservation.controller;

import CampingTrade.catbackend.common.dto.ApiResponse;
import CampingTrade.catbackend.oauth.util.JwtHeaderUtil;
import CampingTrade.catbackend.reservation.dto.ReservationRequestDto;
import CampingTrade.catbackend.reservation.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("reservation/")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://camping-trade.netlify.app")
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation(value = "예약", notes = "예약 페이지 - 캠핑장 예약하기")
    @PostMapping("/{campingId}")
    public ResponseEntity<Void> createReservation(HttpServletRequest request,
                                                  @PathVariable Long campingId,
                                                  @RequestBody ReservationRequestDto dto) {
        String token = JwtHeaderUtil.getAccessToken(request);
        reservationService.createReservation(token, campingId, dto);

        return ApiResponse.success(null);
    }

}
