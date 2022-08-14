package CampingTrade.catbackend.member.entity;

import CampingTrade.catbackend.oauth.entity.RoleType;
import CampingTrade.catbackend.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column
    private int point;
    
    @OneToMany(mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;



}

