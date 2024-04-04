package org.dopaminz.controller;


import lombok.RequiredArgsConstructor;
import org.dopaminz.common.exception.BadRequestException;
import org.dopaminz.common.response.CommonResponse;
import org.dopaminz.controller.request.TestRequest;
import org.dopaminz.controller.response.TestResponse;
import org.dopaminz.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/ex")
    public String exception() {
        throw new BadRequestException("예외 테스트");
    }

    @PostMapping
    public CommonResponse<TestResponse> createTestEntity(@RequestBody TestRequest request) {
        return CommonResponse.created(testService.create(request));
    }
}
