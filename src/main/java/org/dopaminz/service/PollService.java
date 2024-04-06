package org.dopaminz.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dopaminz.controller.request.PollRequest;
import org.dopaminz.controller.response.CommentResponse;
import org.dopaminz.controller.response.PollResponse;
import org.dopaminz.controller.response.PollSimpleResponse;
import org.dopaminz.entity.Category;
import org.dopaminz.entity.Member;
import org.dopaminz.entity.Poll;
import org.dopaminz.entity.Vote;
import org.dopaminz.repository.CommentRepository;
import org.dopaminz.repository.MemberRepository;
import org.dopaminz.repository.PollRepository;
import org.dopaminz.repository.VoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PollService {

    private final PollRepository pollRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    public Page<PollSimpleResponse> getPolls(
            Pageable pageable,
            List<Category> categories,
            Long memberId
    ) {
        Member member = memberRepository.getById(memberId);
        if (categories.isEmpty()) {
            return pollRepository.findAll(pageable)
                    .map(poll -> {
                        Vote vote = voteRepository.findByPollAndMember(poll, member)
                                .orElse(null);
                        return PollSimpleResponse.from(poll, vote);
                    });
        }
        return pollRepository.findAllByCategoryIn(categories, pageable)
                .map(poll -> {
                    Vote vote = voteRepository.findByPollAndMember(poll, member)
                            .orElse(null);
                    return PollSimpleResponse.from(poll, vote);
                });
    }

    public PollResponse getById(Long memberId, Long pollId) {
        Member member = memberRepository.getById(memberId);
        Poll poll = pollRepository.getById(pollId);
        Vote vote = voteRepository.findByPollAndMember(poll, member).orElse(null);
        List<CommentResponse> commentResponse = commentRepository.findByPollId(pollId)
                .stream()
                .map(CommentResponse::from)
                .toList();
        return PollResponse.from(poll, commentResponse, vote);
    }

    public void createPoll(Long memberId, PollRequest request) {
        Member member = memberRepository.getById(memberId);
        Poll poll = Poll.builder()
                .type(request.type())
                .vote1(request.vote1())
                .vote2(request.vote2())
                .title(request.title())
                .content(request.content())
                .endDate(request.endDate())
                .category(request.category())
                .member(member)
                .build();
        pollRepository.save(poll);
    }

    public void deletePoll(Long memberId, Long pollId) {
        Member member = memberRepository.getById(memberId);
        Poll poll = pollRepository.getById(pollId);
        poll.validateOwner(member);
        pollRepository.delete(poll);
    }
}
