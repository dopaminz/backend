package org.dopaminz.repository;

import org.dopaminz.common.exception.NotFoundException;
import org.dopaminz.entity.Member;
import org.dopaminz.entity.Poll;
import org.dopaminz.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    default Vote getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("해당 투표가 존재하지 않습니다."));
    }
    Optional<Vote> findByPollAndMember(Poll poll, Member member);

    boolean existsByPollAndMember(Poll poll, Member member);
}
