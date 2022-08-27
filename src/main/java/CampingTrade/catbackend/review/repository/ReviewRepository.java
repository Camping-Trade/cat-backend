package CampingTrade.catbackend.review.repository;

import CampingTrade.catbackend.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
