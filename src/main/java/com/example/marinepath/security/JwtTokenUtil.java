package com.example.marinepath.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.marinepath.exception.Token.InvalidToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours
    public static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60; // 7 days

    @Value("${jwt.secret}")
    private String secret;

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("role", String.class));
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            Instant expiredOn = e.getClaims().getExpiration().toInstant();
            throw new TokenExpiredException("Token has expired", expiredOn);
        } catch (JwtException e) {
            throw new InvalidToken("Invalid token");
        } catch (Exception e) {
            throw new InvalidToken("Error parsing token: " + e.getMessage());
        }
    }

    public String extractEmail(String token) {
        return getEmailFromToken(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("");
        return doGenerateToken(userDetails.getUsername(), role, JWT_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("");
        return doGenerateToken(userDetails.getUsername(), role, REFRESH_TOKEN_VALIDITY);
    }

    private String doGenerateToken(String subject, String role, long validityInSeconds) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role) // Thêm role vào payload
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        final String role = getRoleFromToken(token); // Lấy role từ token
        return (email.equals(userDetails.getUsername()) &&
                role.equals(userDetails.getAuthorities().stream().findFirst().map(Object::toString).orElse("")) &&
                !isTokenExpired(token));
    }
}
