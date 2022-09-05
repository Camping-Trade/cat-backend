package CampingTrade.catbackend.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private String campingName;
    private Double locationX;
    private Double locationY;

    private String campingDateStart;
    private String campingDateEnd;
    private int numberOfPeople;

    private int usingPoint;
    private int price;

}
