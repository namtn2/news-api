package com.api.news.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Service
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret:null}")
    private String SECRET_KEY;

    @Value("${jwt.expireTime.token:null}")
    private Long expireTime;

    @Value("${jwt.expireTime.refreshToken:null}")
    private Long expireTimeRefresh;

//    public String generateToken(UserDetails userDetails, Long time) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername(), time == null ? expireTime : time);
//    }

    public String generateToken(String userName, Long time) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName, time == null ? expireTime : time);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String username, Long time) {
        return Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return claims;
    }
    
    public String createRefreshToken(String username){
        return createToken(new HashMap(), username, expireTimeRefresh);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Map<String, String> refreshToken(String token) {
        String username = extractUsername(token);
        if (Utils.isStringNullOrEmpty(username)) {
            return null;
        }
        if (isTokenExpired(token)) {
            return null;
        }

        String newToken = createToken(new HashMap(), username, expireTime);
        String refreshToken = createToken(new HashMap(), username, expireTimeRefresh);

        Map<String, String> map = new HashMap<>();
        map.put("token", newToken);
        map.put("refreshToken", refreshToken);

        return map;
    }

}
