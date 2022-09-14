package CampingTrade.catbackend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SharingResponseDto {

    private Long sharingId;
    private String createdDate;
    private String updatedDate;

    private Long campingId;
    private String campingName;
    private int pointToGet;
    private String type;
}
