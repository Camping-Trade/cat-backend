package CampingTrade.catbackend.review.repository;

import CampingTrade.catbackend.review.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
