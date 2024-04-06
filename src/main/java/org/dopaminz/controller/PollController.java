package org.dopaminz.controller;


import lombok.RequiredArgsConstructor;
import org.dopaminz.common.auth.Auth;
import org.dopaminz.common.response.CommonResponse;
import org.dopaminz.common.response.PageResponse;
import org.dopaminz.controller.request.PollRequest;
import org.dopaminz.controller.response.PollResponse;
import org.dopaminz.controller.response.PollSimpleResponse;
import org.dopaminz.entity.Category;
import org.dopaminz.service.PollService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/polls")
public class PollController {

    private final PollService pollService;

    @GetMapping
    public CommonResponse<PageResponse<PollSimpleResponse>> getPolls(
            @RequestParam(defaultValue = "false", required = false) Boolean hot,
            @RequestParam(defaultValue = "DESC", required = false) String createdDate,
            @RequestParam(required = false) List<Category> categories,
            @RequestParam(required = false) int page,
            @Auth Long memberId
            ) {
        Pageable pageable = PageRequest.of(page, 10, sort(hot, createdDate));
        return CommonResponse.ok(PageResponse.from(pollService.getPolls(pageable, categories, memberId)));
    }

    @GetMapping("/{pollId}")
    public CommonResponse<PollResponse> getPoll(
            @Auth Long memberId,
            @PathVariable Long pollId
    ) {
        return CommonResponse.ok(pollService.getById(memberId, pollId));
    }

    @PostMapping
    public CommonResponse<String> createPoll(
            @Auth Long memberId,
            @RequestBody PollRequest request
    ) {
        pollService.createPoll(memberId, request);
         return CommonResponse.ok(null) ;
    }

    @DeleteMapping("/{pollId}")
    public CommonResponse<String> deletePoll(
            @Auth Long memberId,
            @PathVariable Long pollId
    ) {
        pollService.deletePoll(memberId, pollId);
        return CommonResponse.ok(null);
    }

    private Sort sort(boolean hot, String createdDateSort) {
        Order createdDateOrder = (createdDateSort.equals("DESC")) ?
                Order.desc("createdDate") :
                Order.asc("createdDate");
        Order hotOrder = hot ? Order.desc("voteTotalCount") : null;
        if (hotOrder == null) {
            return Sort.by(createdDateSort);
        }
        return Sort.by(hotOrder, createdDateOrder);
    }
}
