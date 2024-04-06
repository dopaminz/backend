package org.dopaminz.repository;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.dopaminz.common.exception.NotFoundException;
import org.dopaminz.entity.Category;
import org.dopaminz.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PollRepository extends JpaRepository<Poll, Long> {

    default Poll getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("해당 퀵폴 혹은 사연이 존재하지 않습니다."));
    }

    default Poll getWithLockById(Long id) {
        return findWithLockById(id)
                .orElseThrow(() -> new NotFoundException("해당 퀵폴 혹은 사연이 존재하지 않습니다."));
    }

    @Query("SELECT p FROM Poll p WHERE p.endDate <= :now")
    Page<Poll> findAllFinishedPolls(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT p FROM Poll p WHERE p.endDate > :now")
    Page<Poll> findAllProcessingPolls(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT p FROM Poll p WHERE p.category IN (:categories) AND p.endDate <= :now")
    Page<Poll> findAllFinishedPollsByCategoryIn(
            @Param("categories") List<Category> categories,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

    @Query("SELECT p FROM Poll p WHERE p.category IN (:categories) AND p.endDate > :now")
    Page<Poll> findAllProcessingPollsByCategoryIn(
            @Param("categories") List<Category> categories,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Poll> findWithLockById(Long id);
}
