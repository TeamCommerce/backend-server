package com.commerce.backendserver.auth.infra.jwt;

import com.commerce.backendserver.global.exception.CommerceException;
import com.google.gson.JsonSyntaxException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.commerce.backendserver.auth.exception.AuthError.TOKEN_EXPIRED;
import static com.commerce.backendserver.auth.exception.AuthError.TOKEN_INVALID;

@Component
public class JwtProvider {

    private final Key secretKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtProvider(
            @Value("${jwt.secret.key}") String secretKey,
            @Value("${jwt.access-token-validity}") long accessTokenValidity,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, accessTokenValidity);
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId, refreshTokenValidity);
    }

    public Long getPayload(String token) {
        return Long.parseLong(
                getClaims(token)
                        .getBody()
                        .getSubject());
    }

    private String createToken(Long id, long expireTime) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiration = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setSubject(String.valueOf(id))
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
            if (!expiration.after(now)) throw CommerceException.of(TOKEN_EXPIRED);
        } catch (ExpiredJwtException e) {
            throw CommerceException.of(TOKEN_EXPIRED);
        } catch (SignatureException | SecurityException | MalformedJwtException |
                 UnsupportedJwtException | JsonSyntaxException e) {
            throw CommerceException.of(TOKEN_INVALID);
        }
    }
}
