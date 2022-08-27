package CampingTrade.catbackend.member.repository;

import CampingTrade.catbackend.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static CampingTrade.catbackend.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Transactional(readOnly = true)
    public Member findByKakaoId(String kakaoId) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.kakaoId.eq(kakaoId))
                .fetchOne();
    }

}
