package org.dopaminz.controller.response;

import org.dopaminz.entity.Member;

public record MemberResponse(
        Long id,
        String nickname
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getNickname()
        );
    }
}
