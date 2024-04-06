package org.dopaminz.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.exception.BadRequestException;
import org.dopaminz.common.exception.ForbiddenException;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int voteNumber;

    public Vote(Poll poll, Member member, int voteNumber) {
        this.poll = poll;
        this.member = member;
        validateVoteNumber(voteNumber);
        this.voteNumber = voteNumber;
        poll.increaseVoteCount(voteNumber);
    }

    private void validateVoteNumber(int voteNumber) {
        if (voteNumber != 1 && voteNumber != 2) {
            throw new BadRequestException("투표 번호는 1번 혹은 2번만 가능합니다.");
        }
    }

    public void validateOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new ForbiddenException("해당 투표에 대한 권한이 없습니다.");
        }
    }

    public void change(int voteNumber) {
        if (this.voteNumber == voteNumber) {
            return;
        }
        poll.changeVote(voteNumber);
    }
}
