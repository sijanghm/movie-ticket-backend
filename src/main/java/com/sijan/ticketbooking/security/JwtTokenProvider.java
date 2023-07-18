package com.sijan.ticketbooking.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {


    private String jwtSecret = "6a4432fe6108042698eee09b5003bb3a8bfddc2ed6f87fc4f233bba0a4005ccb";

    private long jwtExpirationDate = 604800000;

    /**
     * generate token method
     */

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return jwtToken;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

//    get Username from token

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody();

        String username = claims.getSubject();
        return username;

    }

//    validates jwt token

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw  new RuntimeException("Invalid Jwt Token");
        } catch (ExpiredJwtException ex) {
            throw  new RuntimeException("Jwt Expired");
        } catch(UnsupportedJwtException ex) {
            throw  new RuntimeException("unsupported jwt");
        } catch (IllegalArgumentException ex) {
            throw  new RuntimeException("Jwt claim is empty");

        }

    }
}
