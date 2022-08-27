package by.gladyshev.gym.repository;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    List<Member> findByRoleOrRole(MemberRole role, MemberRole role1);
}
