package com.Security.Authify.jwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails userDetails, String role) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("Role", role);
        return createToken(claims, userDetails.getUsername());
    }


    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public Claims verifySignatureAndExtractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) //passed secret key to verify signature
                .build()
                .parseClaimsJws(token) //extract all user data
                .getBody();
    }

    public String extractEmail(String token) {
        return verifySignatureAndExtractAllClaims(token).getSubject();
    }

    public Date getExpiration(String token) {
        return verifySignatureAndExtractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return verifySignatureAndExtractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}