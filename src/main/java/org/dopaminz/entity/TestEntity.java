package org.dopaminz.entity;


import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TestEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Builder
    private TestEntity(String name) {
        this.name = name;
    }
}
