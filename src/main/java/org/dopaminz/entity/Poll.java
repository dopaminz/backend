package org.dopaminz.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

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
}
