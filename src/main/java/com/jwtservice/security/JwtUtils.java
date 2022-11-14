package com.jwtservice.security;

import com.jwtservice.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            return Jwts.builder()
                    .setSubject((userPrincipal.getUsername()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT Signature: {}",e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid JWT Token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("Jwt token is expired: {}",e.getMessage());
        }catch(UnsupportedJwtException e){
            log.error("Jwt is not supported: {}",e.getMessage());
        }catch(IllegalArgumentException e){
            log.error("Jwt claims string is empty: {}",e.getMessage());
        }
        return false;

    }


}
