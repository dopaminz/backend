package org.dopaminz.controller;

import lombok.RequiredArgsConstructor;
import org.dopaminz.common.auth.Auth;
import org.dopaminz.common.auth.TokenService;
import org.dopaminz.common.response.CommonResponse;
import org.dopaminz.controller.request.LoginRequest;
import org.dopaminz.controller.request.SignupRequest;
import org.dopaminz.controller.response.MemberResponse;
import org.dopaminz.controller.response.TokenResponse;
import org.dopaminz.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @PostMapping
    public CommonResponse<TokenResponse> signup(
            @RequestBody SignupRequest request
    ) {
        Long memberId = memberService.signup(request);
        String token = tokenService.createToken(memberId);
        return CommonResponse.ok(new TokenResponse(token));
    }

    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        Long memberId = memberService.login(request);
        String token = tokenService.createToken(memberId);
        return CommonResponse.ok(new TokenResponse(token));
    }

    @GetMapping
    public CommonResponse<MemberResponse> findMyProfile(
            @Auth Long memberId
    ) {
        MemberResponse response = memberService.findByMemberId(memberId);
        return CommonResponse.ok(response);
    }
}
