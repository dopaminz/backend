package org.dopaminz.controller.response;

import org.dopaminz.entity.Poll;

public record VoteCountResponse(
        Integer vote1Count,
        Integer vote2Count
) {
    public static VoteCountResponse from(Poll poll) {
        return switch (poll.getType()) {
            case STORY -> new VoteCountResponse(null, null);
            case QUICK_POLL -> new VoteCountResponse(
                    poll.getVote1Count(),
                    poll.getVote2Count()
            );
        };
    }
}
