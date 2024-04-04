package org.dopaminz.controller.response;

import org.dopaminz.entity.Member;

public record MemberResponse(
        Long id,
        String username
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUsername()
        );
    }
}
