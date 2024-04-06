package org.dopaminz.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dopaminz.common.auth.Auth;
import org.dopaminz.common.response.CommonResponse;
import org.dopaminz.common.response.PageResponse;
import org.dopaminz.controller.request.PollRequest;
import org.dopaminz.controller.response.MyPollSimpleResponse;
import org.dopaminz.controller.response.PollResponse;
import org.dopaminz.controller.response.PollSimpleResponse;
import org.dopaminz.entity.Category;
import org.dopaminz.service.PollService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/polls")
public class PollController {

    private final PollService pollService;

    @GetMapping
    public CommonResponse<PageResponse<PollSimpleResponse>> getPolls(
            @RequestParam(defaultValue = "false", required = false) boolean hot,
            @RequestParam(defaultValue = "DESC", required = false) String createdDate,
            @RequestParam(defaultValue = "false", required = false) boolean isFinish,
            @RequestParam(required = false) List<Category> categories,
            @RequestParam(required = false) int page,
            @Auth Long memberId
    ) {
        Pageable pageable = PageRequest.of(page, 10, sort(hot, createdDate));
        Page<PollSimpleResponse> polls = pollService.getPolls(pageable, categories, memberId, isFinish);
        return CommonResponse.ok(PageResponse.from(polls));
    }

    @GetMapping("/{pollId}")
    public CommonResponse<PollResponse> getPoll(
            @Auth Long memberId,
            @PathVariable Long pollId
    ) {
        return CommonResponse.ok(pollService.getById(memberId, pollId));
    }

    @GetMapping("/my")
    public CommonResponse<List<MyPollSimpleResponse>> getMyPoll(
            @Auth Long memberId
    ) {
        return CommonResponse.ok(pollService.getByMemberId(memberId));
    }

    @PostMapping
    public CommonResponse<String> createPoll(
            @Auth Long memberId,
            @RequestBody PollRequest request
    ) {
        pollService.createPoll(memberId, request);
        return CommonResponse.ok(null);
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
