package com.commerce.backendserver.auth.infra.jwt;

import com.commerce.backendserver.auth.exception.AuthError;
import com.commerce.backendserver.global.exception.CommerceException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtProvider {
    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 60 * 15;
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 14;
    private static final String MEMBER_ID = "member_id";

    private final Key secretKey;

    public JwtProvider(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public Long getPayload(String token) {
        return getClaims(token)
                .getBody()
                .get(MEMBER_ID, Long.class);
    }

    private String createToken(Long id, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put(MEMBER_ID, id);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiration = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiration.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public void validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            Date expiration = claims.getBody().getExpiration();
            Date now = new Date();
            if (!expiration.after(now)) throw CommerceException.of(AuthError.TOKEN_EXPIRED);
        } catch (ExpiredJwtException e) {
            throw CommerceException.of(AuthError.TOKEN_EXPIRED);
        } catch (SignatureException | SecurityException | MalformedJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            throw CommerceException.of(AuthError.TOKEN_INVALID);
        }
    }
}
