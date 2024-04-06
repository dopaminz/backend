package org.dopaminz.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.dopaminz.common.exception.NotFoundException;
import org.dopaminz.entity.Category;
import org.dopaminz.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PollRepository extends JpaRepository<Poll, Long> {

    default Poll getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("해당 퀵폴 혹은 사연이 존재하지 않습니다."));
    }

    default Poll getWithLockById(Long id) {
        return findWithLockById(id)
                .orElseThrow(() -> new NotFoundException("해당 퀵폴 혹은 사연이 존재하지 않습니다."));
    }

    Page<Poll> findAll(Pageable pageable);

    Page<Poll> findAllByCategoryIn(List<Category> categories, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Poll> findWithLockById(Long id);
}
