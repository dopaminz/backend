package org.dopaminz.service;

import lombok.RequiredArgsConstructor;
import org.dopaminz.common.exception.BadRequestException;
import org.dopaminz.controller.request.VoteChangeRequest;
import org.dopaminz.controller.request.VoteRequest;
import org.dopaminz.controller.response.VoteCountResponse;
import org.dopaminz.entity.Member;
import org.dopaminz.entity.Poll;
import org.dopaminz.entity.Vote;
import org.dopaminz.repository.MemberRepository;
import org.dopaminz.repository.PollRepository;
import org.dopaminz.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VoteService {

    private final MemberRepository memberRepository;
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    public VoteCountResponse vote(Long memberId, VoteRequest request) {
        Poll poll = pollRepository.getWithLockById(request.pollId());
        poll.validateEndDate();
        Member member = memberRepository.getById(memberId);
        if (voteRepository.existsByPollAndMember(poll, member)) {
            throw new BadRequestException("이미 참여한 투표입니다.");
        }
        Vote vote = new Vote(poll, member, request.voteNumber());
        voteRepository.save(vote);
        return VoteCountResponse.from(poll);
    }

    public VoteCountResponse change(Long memberId, Long voteId, VoteChangeRequest request) {
        Vote vote = voteRepository.getById(voteId);
        Poll poll = pollRepository.getWithLockById(vote.getPoll().getId());
        poll.validateEndDate();
        Member member = memberRepository.getById(memberId);
        vote.validateOwner(member);
        vote.change(request.voteNumber());
        return VoteCountResponse.from(poll);
    }
}
