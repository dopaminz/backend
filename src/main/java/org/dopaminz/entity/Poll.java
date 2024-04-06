package org.dopaminz.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.entity.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Poll extends BaseEntity<Long> {

    public enum PollType {
        QUICK_POLL,
        STORY;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PollType type;

    private int vote1Count;

    private int vote2Count;

    public void increaseVoteCount(int voteNumber) {
        switch (voteNumber) {
            case 1 -> vote1Count++;
            case 2 -> vote2Count++;
        }
    }

    public void changeVote(int voteNumber) {
        switch (voteNumber) {
            case 1 -> {
                vote1Count++;
                vote2Count--;
            }
            case 2 -> {
                vote2Count++;
                vote1Count--;
            }
        }
    }

    public void validateEndDate() {
        // TODO 구현
    }
}
