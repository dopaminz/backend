package org.dopaminz.common.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.dopaminz.common.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    private static final String MEMBER_ID_CLAIM = "memberId";

    private final SecretKey secretKey;
    private final long accessTokenExpirationMillis;

    public TokenService(TokenProperty tokenProperty) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenProperty.secretKey()));
        this.accessTokenExpirationMillis =
                tokenProperty.accessTokenExpirationMillis();
    }

    public String createToken(Long memberId) {
        return Jwts.builder()
                .claim(MEMBER_ID_CLAIM, memberId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Long extractMemberId(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(MEMBER_ID_CLAIM, Long.class);
        } catch (Exception e) {
            throw new UnauthorizedException("토큰 정보가 잘못되었습니다.");
        }
    }
}
