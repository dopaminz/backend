package org.dopaminz.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dopaminz.controller.request.PollRequest;
import org.dopaminz.controller.response.CommentResponse;
import org.dopaminz.controller.response.MyPollSimpleResponse;
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

@Slf4j
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
            Long memberId,
            boolean isFinish
    ) {
        Member member = memberRepository.getById(memberId);
        Page<Poll> response;
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(categories)) {
            if (isFinish) {
                response = pollRepository.findAllFinishedPolls(now, pageable);
            } else {
                response = pollRepository.findAllProcessingPolls(now, pageable);
            }
        } else {
            if (isFinish) {
                response = pollRepository.findAllFinishedPollsByCategoryIn(categories, now, pageable);
            } else {
                response = pollRepository.findAllProcessingPollsByCategoryIn(categories, now, pageable);
            }
        }
        return response.map(poll -> {
            Vote vote = voteRepository.findByPollAndMember(poll, member).orElse(null);
            log.info("findByPollAndMember.poll and vote {} {}", poll.getId(), vote);
            return PollSimpleResponse.from(poll, vote);
        });
    }

    public List<MyPollSimpleResponse> getByMemberId(Long memberId) {
        Member member = memberRepository.getById(memberId);
        return pollRepository.findAllByMemberOrderByCreatedDateDesc(member)
                .stream()
                .map(MyPollSimpleResponse::from)
                .toList();
    }

    public PollResponse getById(Long memberId, Long pollId) {
        Member member = memberRepository.getById(memberId);
        Poll poll = pollRepository.getById(pollId);
        Vote vote = voteRepository.findByPollAndMember(poll, member).orElse(null);
        log.info("getById.poll and vote {} {}", poll.getId(), vote);
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
