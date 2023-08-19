package com.commerce.backendserver.auth.infra.jwt;

import com.commerce.backendserver.auth.exception.AuthError;
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

@Component
public class JwtProvider {
    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 60 * 15;
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 14;

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
                 UnsupportedJwtException | IllegalArgumentException | JsonSyntaxException e) {
            throw CommerceException.of(AuthError.TOKEN_INVALID);
        }
    }

    public static void main(String[] args) {
        JwtProvider provider = new JwtProvider("abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc");
        String token = provider.createToken(1L, (60 * 60 * 24 * 365 * 20));
        System.out.println(token);
    }
}
