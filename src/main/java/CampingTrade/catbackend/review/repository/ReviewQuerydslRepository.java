package CampingTrade.catbackend.review.repository;

import CampingTrade.catbackend.review.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
                .where(review.campingId.eq(campingId)
                        .and(review.reviewId.eq(reviewId)))
                .execute();

        em.flush();
        em.clear();
    }
}
