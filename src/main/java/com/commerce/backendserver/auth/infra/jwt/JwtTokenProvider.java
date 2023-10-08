package com.commerce.backendserver.auth.infra.jwt;

import static com.commerce.backendserver.auth.exception.AuthError.*;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.commerce.backendserver.global.exception.CommerceException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtTokenProvider {

	private final Key secretKey;
	private final long accessTokenValidity;
	private final long refreshTokenValidity;

	public JwtTokenProvider(
		@Value("${jwt.secret.key}") final String secretKey,
		@Value("${jwt.access-token-validity}") final long accessTokenValidity,
		@Value("${jwt.refresh-token-validity}") final long refreshTokenValidity
	) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
		this.accessTokenValidity = accessTokenValidity;
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String createAccessToken(final Long memberId) {
		return createToken(memberId, accessTokenValidity);
	}

	public String createRefreshToken(final Long memberId) {
		return createToken(memberId, refreshTokenValidity);
	}

	public Long getPayload(final String token) {
		return Long.parseLong(
			getClaims(token)
				.getBody()
				.getSubject()
		);
	}

	private String createToken(final Long id, final long expireTime) {
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + expireTime);

		return Jwts.builder()
			.setSubject(String.valueOf(id))
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public void validateToken(final String token) {
		getClaims(token);
	}

	private Jws<Claims> getClaims(final String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
		} catch (final ExpiredJwtException e) {
			throw CommerceException.of(TOKEN_EXPIRED);
		} catch (final SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e
		) {
			throw CommerceException.of(TOKEN_INVALID);
		}
	}
}
