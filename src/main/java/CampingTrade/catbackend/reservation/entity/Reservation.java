package CampingTrade.catbackend.reservation.entity;

import CampingTrade.catbackend.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "camping_id")
    private Long campingId;

    @Column(name = "camping_name")
    private String campingName;

    @Column(name = "location_x")
    private Double locationX;

    @Column(name = "location_y")
    private Double locationY;

    @Column(name = "camping_date_start")
    private String campingDateStart;

    @Column(name = "camping_date_end")
    private String campingDateEnd;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "using_point")
    private int usingPoint;

    @Column(name = "remaining_point")
    private int remainingPoint;

    @Column
    private int price;

    @Column(name = "reservation_date")
    private String reservationDate;


}
