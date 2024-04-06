package org.dopaminz.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.entity.BaseEntity;
import org.dopaminz.common.exception.ForbiddenException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 200)
    private String content;

    public Comment(Poll poll, Member member, String content) {
        this.poll = poll;
        this.member = member;
        this.content = content;
    }

    public void validateOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new ForbiddenException("해당 투표에 대한 권한이 없습니다.");
        }
    }
}
