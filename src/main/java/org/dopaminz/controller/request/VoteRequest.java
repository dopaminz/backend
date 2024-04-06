package org.dopaminz.controller.request;

public record VoteRequest(
        Long pollId,
        int voteNumber
) {
}
