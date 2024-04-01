package org.dopaminz.controller.response;

import org.dopaminz.entity.TestEntity;

public record TestResponse(
    Long id,
    String name
) {
    public static TestResponse from(TestEntity testEntity) {
        return new TestResponse(testEntity.getId(), testEntity.getName());
    }
}
