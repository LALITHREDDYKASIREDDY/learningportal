package com.example.LearningPortal.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	// Method to generate JWT token
	public String generateToken(String userName) {
		logger.info("Generating token for user: {}", userName);
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	// Method to create token
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	// Method to get signing key
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Method to extract username from token
	public String extractUsername(String token) {
		logger.info("Extracting username from token");
		return extractClaim(token, Claims::getSubject);
	}

	// Method to extract expiration date from token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Method to extract claims from token
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Method to extract all claims from token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	// Method to check if token is expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Method to validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		logger.info("Validating token");
		final String username = extractUsername(token);
		Boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		logger.info("Token validation result: {}", isValid);
		return isValid;
	}
}
