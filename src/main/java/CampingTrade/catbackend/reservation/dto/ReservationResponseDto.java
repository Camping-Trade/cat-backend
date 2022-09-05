package CampingTrade.catbackend.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDto {

    private Long reservationId;
    private String nickname;

    private Long campingId;
    private String campingName;

    private String campingDateStart;
    private String campingDateEnd;
    private String reservationDate;
    private int numberOfPeople;

    private int usingPoint;
    private int price;
}
