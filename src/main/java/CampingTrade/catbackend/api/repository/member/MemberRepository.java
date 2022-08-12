package CampingTrade.catbackend.api.repository.member;

import CampingTrade.catbackend.api.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById (Long id);

}
