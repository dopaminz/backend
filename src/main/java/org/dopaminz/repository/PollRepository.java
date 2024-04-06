package org.dopaminz.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.dopaminz.common.exception.NotFoundException;
import org.dopaminz.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PollRepository extends JpaRepository<Poll, Long> {

    default Poll getWithLockById(Long id) {
        return findWithLockById(id)
                .orElseThrow(() -> new NotFoundException("해당 퀵폴 혹은 사연이 존재하지 않습니다."));
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Poll> findWithLockById(Long id);
}
