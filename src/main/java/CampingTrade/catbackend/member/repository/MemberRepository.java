package CampingTrade.catbackend.member.repository;

import CampingTrade.catbackend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById (Long memberId);

}
