package com.springsecurity.springsecurity.security;

import com.springsecurity.springsecurity.repository.AccessTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String ISSUER = "friday";
    private static final SecretKey JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int expirationInMs = 3600000;
    private final AccessTokenRepository accessTokenRepository;

    public JwtTokenProvider(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public String createToken(SecurityUser securityUser) {

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(expirationInMs);

        Claims claims = Jwts.claims()
                .setSubject("colini");
        claims.put("id", securityUser.getId());
        claims.put("name", securityUser.getName());
        claims.put("email", securityUser.getUsername());
        claims.put("role", securityUser.getRole());

        String accessToken = generate(securityUser, currentTime, expirationTime, claims);

        saveToken(securityUser.getId(), accessToken, expirationTime);

        return accessToken;
    }

    private void saveToken(long id,
                           String rawToken,
                           LocalDateTime expirationTime) {
        AccessToken accessToken = AccessToken.builder()
                .id(id)
                .rawToken(rawToken)
                .refreshTime(expirationTime)
                .build();

        accessTokenRepository.save(accessToken);
    }

    private static String generate(UserDetails userDetails,
                                   LocalDateTime currentTime,
                                   LocalDateTime expirationTime,
                                   Claims claims) {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(userDetails.getUsername())
                .addClaims(claims)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(JWT_KEY)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            decodeJwt(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Not Match Token : " + e);
        }
    }

    public Claims decode(String token) {
        return decodeJwt(token).getBody();
    }

    private Jws<Claims> decodeJwt(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_KEY)
                .parseClaimsJws(token);
    }

    public boolean verify(String token) {
        return tokenVerify(token) && validateToken(token);
    }

    private boolean tokenVerify(String token) {
        Claims decode = decode(token);
        long id = Long.parseLong(decode.get("id").toString());
        return accessTokenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found token " + token))
                .verify(token);
    }
}
