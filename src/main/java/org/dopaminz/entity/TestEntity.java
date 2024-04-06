package org.dopaminz.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.entity.BaseEntity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TestEntity extends BaseEntity<Long> {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Builder
    private TestEntity(String name) {
        this.name = name;
    }
}
