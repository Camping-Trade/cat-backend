package CampingTrade.catbackend.reservation.entity;

import CampingTrade.catbackend.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mariadb.jdbc.type.Point;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "member_id")
    private Member member;

    @Column(name = "campsite_name", nullable = false)
    private String campsiteName;

    @Column(name = "location_address")
    private String locationAddress;

    //@Column(name = "location_point")
    //private Point locationPoint;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "redeem_point")
    private int redeemPoint;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

}
