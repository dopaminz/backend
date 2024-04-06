package org.dopaminz.controller;

import lombok.RequiredArgsConstructor;
import org.dopaminz.common.auth.Auth;
import org.dopaminz.common.response.CommonResponse;
import org.dopaminz.controller.request.CommentWriteRequest;
import org.dopaminz.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommonResponse<String> write(
            @Auth Long memberId,
            @RequestBody CommentWriteRequest request
    ) {
        commentService.write(memberId, request);
        return CommonResponse.ok(null);
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse<String> write(
            @Auth Long memberId,
            @PathVariable Long commentId
    ) {
        commentService.delete(memberId, commentId);
        return CommonResponse.ok(null);
    }
}
