package org.dopaminz.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import org.dopaminz.entity.Category;
import org.dopaminz.entity.Poll;
import org.dopaminz.entity.Vote;

public record PollResponse(
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
        List<CommentResponse> comments
) {

    public static PollResponse from(Poll poll, List<CommentResponse> comments, Vote vote) {
        Boolean isVoted = vote != null;
        int votedNumber = isVoted ? vote.getVoteNumber() : 0;
        return new PollResponse(
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
                comments
        );
    }

}
