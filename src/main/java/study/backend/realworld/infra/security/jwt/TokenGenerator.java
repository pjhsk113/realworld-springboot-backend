package study.backend.realworld.infra.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import study.backend.realworld.application.user.domain.Email;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TokenGenerator {
    @Value("${token.sessionTime}")
    private Integer sessionTime;
    @Value("${token.signKey}")
    private String signKey;

    public String generateToken(Email email) {
        LocalDateTime currentTime = LocalDateTime.now();
        return Jwts.builder()
                .setSubject(email.getEmail())
                .setExpiration(Date.from(
                        currentTime.plusMinutes(sessionTime)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
    }
}
