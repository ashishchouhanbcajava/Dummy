package com.Dummy.Dummy.configurations;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtUtil {

	static String secret = "abcdefghijklmnopqrstuvwxyz1234567890";
	static SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

	public static String generateToken(String username) {
		Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
		return Jwts.builder().subject(username).issuedAt(new Date()).expiration(expiryDate).signWith(key).compact();
	}

}
