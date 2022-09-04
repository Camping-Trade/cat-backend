package CampingTrade.catbackend.member.repository;

import CampingTrade.catbackend.member.entity.Member;
import CampingTrade.catbackend.member.dto.MemberResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static CampingTrade.catbackend.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    //private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public Member findByKakaoId(String kakaoId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .selectFrom(member)
                .where(member.kakaoId.eq(kakaoId))
                .fetchOne();
    }

    @Transactional(readOnly = true)
    public MemberResponse findByMemberId(Long memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(Projections.fields(MemberResponse.class,
                        member.nickname,
                        member.email,
                        member.profileImageUrl,
                        member.thumbnailImageUrl,
                        member.point))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
    }

}
