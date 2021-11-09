package com.nashtech.minhtran.gearshop.security.jwt;

import com.nashtech.minhtran.gearshop.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private  static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${nashtech.app.jwtSecret}")
    private String jwtSecret;

    @Value("${nashtech.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrinciple = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userPrinciple.getUsername()))
                .setIssuedAt(new Date()) // set start time of token
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // set time expiration in millisecond
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // algorithm
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken (String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
