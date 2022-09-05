package CampingTrade.catbackend.reservation.repository;

import CampingTrade.catbackend.reservation.dto.ReservationResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static CampingTrade.catbackend.reservation.entity.QReservation.reservation;

@Repository
@RequiredArgsConstructor
public class ReservationQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<ReservationResponseDto> findReservationListByMemberId(Long memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<ReservationResponseDto> reservationResponseDtoList = jpaQueryFactory
                .select(Projections.fields(ReservationResponseDto.class,
                        reservation.reservationId,
                        reservation.member.nickname,
                        reservation.campingId,
                        reservation.campingName,
                        reservation.campingDateStart,
                        reservation.campingDateEnd,
                        reservation.reservationDate,
                        reservation.numberOfPeople,
                        reservation.usingPoint,
                        reservation.price
                        ))
                .from(reservation)
                .where(reservation.member.id.eq(memberId))
                .groupBy(reservation.reservationId)
                .orderBy(reservation.reservationId.desc())
                .fetch();

        return reservationResponseDtoList;
    }
}
