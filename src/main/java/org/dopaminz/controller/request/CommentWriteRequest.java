package org.dopaminz.controller.request;

public record CommentWriteRequest(
        Long pollId,
        String content
) {
}
