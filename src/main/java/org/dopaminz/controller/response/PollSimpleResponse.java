package org.dopaminz.controller.response;

import java.time.LocalDateTime;
import org.dopaminz.entity.Category;
import org.dopaminz.entity.Poll;
import org.dopaminz.entity.Vote;

public record PollSimpleResponse(
        Long pollId,
        Poll.PollType type,
        String title,
        String content,
        LocalDateTime endDate,
        String vote1,
        String vote2,
        int vote1Count,
        int vote2Count,
        Category category,
        Boolean isVoted,
        int votedNumber,
        LocalDateTime createdDate
) {
    public static PollSimpleResponse from(Poll poll, Vote vote) {
        Boolean isVoted = vote != null;
        int votedNumber = isVoted ? vote.getVoteNumber() : 0;
        return new PollSimpleResponse(
                poll.getId(),
                poll.getType(),
                poll.getTitle(),
                poll.getContent(),
                poll.getEndDate(),
                poll.getVote1(),
                poll.getVote2(),
                poll.getVote1Count(),
                poll.getVote2Count(),
                poll.getCategory(),
                isVoted,
                votedNumber,
                poll.getCreatedDate()
        );
    }
}
