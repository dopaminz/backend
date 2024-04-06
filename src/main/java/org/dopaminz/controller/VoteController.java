package org.dopaminz.controller;

import lombok.RequiredArgsConstructor;
import org.dopaminz.common.auth.Auth;
import org.dopaminz.common.response.CommonResponse;
import org.dopaminz.controller.request.VoteChangeRequest;
import org.dopaminz.controller.request.VoteRequest;
import org.dopaminz.controller.response.VoteCountResponse;
import org.dopaminz.service.VoteService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/votes")
@RestController
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public CommonResponse<VoteCountResponse> vote(
            @Auth Long memberId,
            @RequestBody VoteRequest request
    ) {
        return CommonResponse.ok(voteService.vote(memberId, request));
    }

    @PutMapping("/{voteId}")
    public CommonResponse<VoteCountResponse> change(
            @Auth Long memberId,
            @PathVariable Long voteId,
            @RequestBody VoteChangeRequest request
    ) {
        return CommonResponse.ok(voteService.change(memberId, voteId, request));
    }
}
