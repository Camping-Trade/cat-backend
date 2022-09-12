package CampingTrade.catbackend.member.repository;

import CampingTrade.catbackend.member.dto.SharingResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static CampingTrade.catbackend.member.entity.QSharing.sharing;

@Repository
@RequiredArgsConstructor
public class SharingQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<SharingResponseDto> findSharingListByMemberId(Long memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<SharingResponseDto> sharingResponseDtoList = jpaQueryFactory
                .select(Projections.fields(SharingResponseDto.class,
                        sharing.id.as("sharingId"),
                        sharing.campingId,
                        sharing.createdDate,
                        sharing.updatedDate,
                        sharing.type,
                        sharing.pointToGet
                ))
                .from(sharing)
                .where(sharing.member.id.eq(memberId))
                .groupBy((sharing.id))
                .orderBy(sharing.id.desc())
                .fetch();

        return sharingResponseDtoList;

    }
}
