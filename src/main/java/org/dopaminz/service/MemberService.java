package org.dopaminz.service;

import lombok.RequiredArgsConstructor;
import org.dopaminz.common.exception.ConflictException;
import org.dopaminz.common.exception.UnauthorizedException;
import org.dopaminz.controller.request.LoginRequest;
import org.dopaminz.controller.request.SignupRequest;
import org.dopaminz.controller.response.MemberResponse;
import org.dopaminz.entity.Member;
import org.dopaminz.repository.MemberRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long signup(SignupRequest request) {
        if (memberRepository.existsByUsername(request.username())) {
            throw new ConflictException("중복된 아이디입니다.");
        }
        Member member = request.toMember();
        return memberRepository.save(member)
                .getId();
    }

    public Long login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new UnauthorizedException("없는 아이디입니다."));
        member.login(request.password());
        return member.getId();
    }

    public MemberResponse findByMemberId(Long memberId) {
        Member member = memberRepository.getById(memberId);
        return MemberResponse.from(member);
    }
}
