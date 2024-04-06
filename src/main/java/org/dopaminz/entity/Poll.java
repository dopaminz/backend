package org.dopaminz.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.entity.BaseEntity;
import org.dopaminz.common.exception.BadRequestException;
import org.dopaminz.common.exception.ForbiddenException;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Poll extends BaseEntity<Long> {

    private static final int DEFAULT_VOTE_COUNT = 0;

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

    private String vote1;

    private String vote2;

    private int vote1Count;

    private int vote2Count;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalVoteCount;

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

    public void validateOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new ForbiddenException("해당 퀵폴&사연에 대한 권한이 없습니다.");
        }
    }

    public void validateEndDate() {
        if (endDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("투표가 종료된 퀵폴&사연입니다.");
        }
    }

    @Builder
    private Poll(PollType type, String vote1, String vote2, String title, String content, LocalDateTime endDate, Category category, Member member) {
        this.type = type;
        this.vote1 = vote1;
        this.vote2 = vote2;
        this.vote1Count = DEFAULT_VOTE_COUNT;
        this.vote2Count = DEFAULT_VOTE_COUNT;
        this.title = title;
        this.content = content;
        this.endDate = initializeEndDate(type, endDate);
        this.category = category;
        this.member = member;
    }

    private LocalDateTime initializeEndDate(PollType type, LocalDateTime endDate) {
        if (type.equals(PollType.QUICK_POLL)) {
            return LocalDateTime.now().plusDays(1);
        }
        if (endDate == null) {
            throw new BadRequestException("종료일은 필수 입력값입니다.");
        }

        if (endDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("종료일은 현재 시간 이후로 설정해야 합니다.");
        }
        return endDate;
    }
}
