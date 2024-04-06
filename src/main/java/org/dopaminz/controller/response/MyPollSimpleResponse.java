package org.dopaminz.controller.response;

import java.time.LocalDateTime;
import org.dopaminz.entity.Category;
import org.dopaminz.entity.Poll;

public record MyPollSimpleResponse(
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
        LocalDateTime createdDate
) {
    public static MyPollSimpleResponse from(Poll poll) {
        return new MyPollSimpleResponse(
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
                poll.getCreatedDate()
        );
    }
}
