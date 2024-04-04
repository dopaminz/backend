package org.dopaminz.repository;

import java.util.Optional;
import org.dopaminz.common.exception.NotFoundException;
import org.dopaminz.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("회원을 정보를 찾을 수 없습니다.")
        );
    }

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
