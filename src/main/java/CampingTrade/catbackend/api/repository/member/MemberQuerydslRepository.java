package CampingTrade.catbackend.api.repository.member;

import CampingTrade.catbackend.api.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static CampingTrade.catbackend.api.entity.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Member findById(Long id) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }
}
