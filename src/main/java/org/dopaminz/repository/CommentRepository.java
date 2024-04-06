package org.dopaminz.repository;

import org.dopaminz.common.exception.NotFoundException;
import org.dopaminz.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("해당 댓글이 존재하지 않습니다."));
    }

    @EntityGraph(attributePaths = {"member"})
    List<Comment> findByPollId(Long pollId);
}
