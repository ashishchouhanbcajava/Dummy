package com.Dummy.Dummy.configurations;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Dummy.Dummy.beans.Users;
import com.Dummy.Dummy.service.UsersService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	

	static String secret = "abcdefghijklmnopqrstuvwxyz1234567890";
	static SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

	public static String generateToken(String username) {
		Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
		return Jwts.builder().subject(username).issuedAt(new Date()).expiration(expiryDate).signWith(key).compact();
	}

	public static Claims extractClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	}

	public static String extractUsername(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token).getSubject();
	}

	public static boolean verifyToken(String username, String token, UserDetails users) {
		// TODO Auto-generated method stub

		return username.equals(users.getUsername()) && !isTokenExpired(token);
	}

	public static boolean isTokenExpired(String token) {
		Claims claims = extractClaims(token);
		return claims.getExpiration().before(new Date());
	}

}
