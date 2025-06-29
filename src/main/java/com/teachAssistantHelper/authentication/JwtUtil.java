package com.teachAssistantHelper.authentication;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    // JWT 생성
    public String createToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // JWT 검증 + username 반환
    public String validateAndGetUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token.");
        }
    }

    public Cookie createCookie(String jwtToken) {
        Cookie cookie = new Cookie("access_token", jwtToken);
        cookie.setHttpOnly(true); // JS 조작 불가(XSS 방지)
        cookie.setSecure(true);   // HTTPS 전용
        cookie.setPath("/");      // 전체 경로에 대해 쿠키 유효
        cookie.setMaxAge(60 * 60); // 1시간 동안 유지

        return cookie;
    }

    public void addJwtCookie(HttpServletResponse response, String jwtToken) {
        int maxAge = 60 * 60; // 1시간

        String cookieValue = "access_token=" + jwtToken +
                "; Path=/" +
                "; HttpOnly" +
                "; Secure" +
                "; SameSite=None" +
                "; Max-Age=" + maxAge;

        response.setHeader("Set-Cookie", cookieValue);
    }
}
