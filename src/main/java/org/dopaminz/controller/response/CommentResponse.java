package org.dopaminz.controller.response;

import org.dopaminz.entity.Comment;
import org.dopaminz.entity.Member;

import java.time.LocalDateTime;

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
