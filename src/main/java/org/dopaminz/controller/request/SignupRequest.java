package org.dopaminz.controller.request;

import org.dopaminz.entity.Member;

public record SignupRequest(
        String username,
        String password
) {
    public Member toMember() {
        return new Member(username, password);
    }
}
