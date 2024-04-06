package org.dopaminz.service;

import lombok.RequiredArgsConstructor;
import org.dopaminz.controller.request.CommentWriteRequest;
import org.dopaminz.entity.Comment;
import org.dopaminz.entity.Member;
import org.dopaminz.entity.Poll;
import org.dopaminz.repository.CommentRepository;
import org.dopaminz.repository.MemberRepository;
import org.dopaminz.repository.PollRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final MemberRepository memberRepository;
    private final PollRepository pollRepository;
    private final CommentRepository commentRepository;

    public void write(Long memberId, CommentWriteRequest request) {
        Member member = memberRepository.getById(memberId);
        Poll poll = pollRepository.getById(request.pollId());
        Comment comment = new Comment(poll, member, request.content());
        commentRepository.save(comment);
    }

    public void delete(Long memberId, Long commentId) {
        Member member = memberRepository.getById(memberId);
        Comment comment = commentRepository.getById(commentId);
        comment.validateOwner(member);
        commentRepository.delete(comment);
    }
}
