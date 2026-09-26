package com.Security.Authify.jwtUtils;

import com.Security.Authify.entity.InvitationEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class InvitationTokenProvider {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    @Value("${invitation.expiration-hours}")
    private int expirationHours;

    public String generateToken(InvitationEntity invitation){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (long) expirationHours * 60 * 60 *1000);
        System.out.println("ID = " + invitation.getId());
        return Jwts.builder()
                .setSubject(invitation.getEmail())
                .claim("invitation_id", invitation.getId())
                .claim("role", invitation.getInvitedRole().getName())
                .claim("type", "INVITATION")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public Claims validateAndExtract(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return validateAndExtract(token).getSubject();
    }

    public Long extractInvitationId(String token){
        return validateAndExtract(token).get("invitation_id", Long.class);
    }
}
