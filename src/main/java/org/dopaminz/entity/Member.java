package org.dopaminz.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dopaminz.common.exception.UnauthorizedException;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void login(String password) {
        if (!this.password.equals(password)) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }
}
