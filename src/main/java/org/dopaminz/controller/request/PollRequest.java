package org.dopaminz.controller.request;

import org.dopaminz.entity.Category;
import org.dopaminz.entity.Poll;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PollRequest(
        Poll.PollType type,
        String vote1,
        String vote2,
        String title,
        String content,
        @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm")
        LocalDateTime endDate,
        Category category
) {
}
