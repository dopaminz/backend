package org.dopaminz.service;

import lombok.RequiredArgsConstructor;
import org.dopaminz.controller.request.TestRequest;
import org.dopaminz.controller.response.TestResponse;
import org.dopaminz.entity.TestEntity;
import org.dopaminz.repository.TestEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {

    private final TestEntityRepository testEntityRepository;

    public TestResponse create(final TestRequest request) {
        TestEntity testEntity = TestEntity.builder()
                        .name(request.name())
                        .build();
        TestEntity savedTestEntity = testEntityRepository.save(testEntity);
        return TestResponse.from(savedTestEntity);
    }
}
