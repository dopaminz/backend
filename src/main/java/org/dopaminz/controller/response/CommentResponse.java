package org.dopaminz.controller.response;

import java.time.LocalDateTime;
import org.dopaminz.entity.Comment;

public record CommentResponse(
        Long commentId,
        Long memberId,
        String nickname,
        String content,
        LocalDateTime createdDate
) {

    public static CommentResponse from(
            Comment comment
    ) {
        return new CommentResponse(
                comment.getId(),
                comment.getMember().getId(),
                comment.getMember().getNickname(),
                comment.getContent(),
                comment.getCreatedDate()
        );
    }
}
