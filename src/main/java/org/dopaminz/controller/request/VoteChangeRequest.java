package org.dopaminz.controller.request;

public record VoteChangeRequest(
        Long voteId,
        int voteNumber
) {
}
