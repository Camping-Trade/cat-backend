package CampingTrade.catbackend.review.repository;

import CampingTrade.catbackend.review.dto.ReviewResponseDto;
import CampingTrade.catbackend.review.entity.Review;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static CampingTrade.catbackend.review.entity.QReview.review;
@Repository
@RequiredArgsConstructor
public class ReviewQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public Review findByCampingIdAndReviewId(Long campingId, Long reviewId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .selectFrom(review)
                .where(review.campingId.eq(campingId)
                        .and(review.reviewId.eq(reviewId)))
                .fetchOne();
    }

    @Transactional
    public void updateReview(String content, int rating, Long campingId, Long reviewId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        long update = jpaQueryFactory
                .update(review)
                .set(review.content, content)
                .set(review.rating, rating)
                .set(review.modifiedDate, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .where(review.campingId.eq(campingId)
                        .and(review.reviewId.eq(reviewId)))
                .execute();

        em.flush();
        em.clear();
    }


    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findReviewListByCampingId(Long campingId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<ReviewResponseDto> reviewResponseDtoList = jpaQueryFactory
                .select(Projections.fields(ReviewResponseDto.class,
                        review.reviewId,
                        review.content,
                        review.rating,
                        review.createdDate,
                        review.modifiedDate,
                        review.writer.nickname.as("writer"),
                        review.campingId,
                        review.images))
                .from(review)
                .where(review.campingId.eq(campingId))
                .groupBy(review.reviewId)
                .orderBy(review.reviewId.desc())
                .fetch();

        return reviewResponseDtoList;
    }
}
