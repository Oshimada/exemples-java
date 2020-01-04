package com.otakuma.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.otakuma.services.admin.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils {

	@Autowired
	Utils utils;
	
	@Autowired
	TokenService accestokenservice;

	private final String CLE = "HykBF20i3r2Li9GbmQdTmiibeUvl8HOa";
	/** minutes */
	private final int EXP_H = 0;
	private final int EXP_M = 15;
	private final int EXP_S = 0;
	
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
			.setSigningKey(CLE)
			.parseClaimsJws(token)
			.getBody();
	}


	public String generateToken(UserDetails user) {
		Map<String, Object> cl = new HashMap<>();
		
		Timestamp debut = utils.now();
		Timestamp   fin = utils.nowPlus(EXP_H, EXP_M, EXP_S);
		
		String token = accestokenservice.genereToken(user, debut, fin);
		return createToken(cl, token, debut, fin);
	}

	private String createToken(Map<String, Object> claims, String username, Date debut, Date fin) {
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(username)
			.setIssuedAt(debut)
			.setExpiration(fin)
			.signWith(SignatureAlgorithm.HS256, CLE).compact();
	}

	private Boolean isTokenExpired(String token) {
		Boolean expired = extractExpiration(token).before(utils.now());
		accestokenservice.cacheEvictOldToken(extractUsername(token));
		return expired;
	}
	
	public boolean validateToken(String pseudo, String jwtoken, UserDetails details) {
		return pseudo.equals(details.getUsername()) && !isTokenExpired(jwtoken);
	}
}
