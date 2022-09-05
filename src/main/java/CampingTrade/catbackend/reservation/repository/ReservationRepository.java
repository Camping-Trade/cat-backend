package CampingTrade.catbackend.reservation.repository;

import CampingTrade.catbackend.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
