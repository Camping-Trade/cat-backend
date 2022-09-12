package CampingTrade.catbackend.member.entity;

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
@Table(name = "share_record")
public class Sharing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String type;

    @Column(name = "created_at")
    private String createdDate;

    @Column(name = "updated_at")
    private String updatedDate;

    @Column(name = "camp_id")
    private Long campingId;

    @Column(name = "point_to_get")
    private int pointToGet;

}
