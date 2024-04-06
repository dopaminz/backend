package org.dopaminz.controller.request;

public record LoginRequest(
        String username,
        String password
) {
}
