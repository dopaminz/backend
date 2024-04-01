package org.dopaminz.controller;


import lombok.RequiredArgsConstructor;
import org.dopaminz.controller.request.TestRequest;
import org.dopaminz.controller.response.TestResponse;
import org.dopaminz.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }

    @PostMapping("/test")
    public TestResponse createTestEntity(@RequestBody TestRequest request) {
        return testService.create(request);
    }
}
