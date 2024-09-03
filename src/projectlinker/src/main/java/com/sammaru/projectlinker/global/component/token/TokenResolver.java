package com.sammaru.projectlinker.global.component.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenResolver {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public Long getAccessClaims(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(removePrefix(token))
                .getBody();

        return claims.get("USER_ID", Long.class);
    }

    public Long getRefreshClaims(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(removePrefix(token))
                .getBody();

        return claims.get("USER_ID", Long.class);
    }

    public long getExpiration(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(removePrefix(token))
                .getBody()
                .getExpiration()
                .getTime();
    }

    private String removePrefix(String token){
        return token.replace("Bearer ", "");
    }
}
