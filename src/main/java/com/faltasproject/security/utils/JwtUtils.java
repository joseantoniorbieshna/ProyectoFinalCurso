package com.faltasproject.security.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;


@Component
public class JwtUtils {
	
	@Value("${security.jwt.key.private}")
	private String privateKey;
	
	@Value("${security.jwt.key.generator}")
	private String userGenerator;
	
	public String createToken(Authentication authentication) {
		int expirationMinutesToken = 30;
		
		Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
		String username = authentication.getPrincipal().toString();
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		
		return JWT.create()
				.withIssuer(this.userGenerator)
				.withSubject(username)
				.withClaim("role", authorities)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+(expirationMinutesToken*60000)))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis())) // a partir de que momento el token es valido
				.sign(algorithm);
		
	}
	
	public DecodedJWT validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
			
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(userGenerator)
					.build();
			return verifier.verify(token);
		} catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Token invalido, no autorizado");
		}
	}

	public String extractUsername(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject();
	}
	
	public Claim getEspecificClaim(DecodedJWT decodedJWT, String claimName) {
		return decodedJWT.getClaim(claimName);
	}
	
	public Map<String, Claim> returnAll(DecodedJWT decodedJWT){
		return decodedJWT.getClaims();
	}
}
