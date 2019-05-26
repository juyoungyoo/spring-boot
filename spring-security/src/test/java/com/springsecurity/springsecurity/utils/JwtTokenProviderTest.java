package com.springsecurity.springsecurity.utils;

import com.springsecurity.springsecurity.AbstractTest;
import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.repository.AccessTokenRepository;
import com.springsecurity.springsecurity.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenProviderTest extends AbstractTest {

    @InjectMocks
    JwtTokenProvider jwtTokenProvider;
    @Mock
    AccessTokenRepository accessTokenRepository;

    SecretKey JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    int expirationInMs = 3600000;

    @Test
    public void jwtTokenGenerator() {
        String jwt = jwtTokenProvider.createToken(getSecurityUser());

        given(accessTokenRepository.save(any())).willReturn(null);

        System.out.println(jwt);
        assertThat(jwt).isNotNull();
    }

    @Test
    public void JwtToken_validate_success() {
        String jwtToken = jwtTokenProvider.createToken(getSecurityUser());

        boolean result = jwtTokenProvider.validateToken(jwtToken);

        assertThat(result).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void jwtToken_validate_fail() {
        String nonToken ="eyJhbGciOiJIUzI1NiJ9.yJzdWIiOiJqdXlvdW5nIiwiaWQiOjEsInVzZXJuYW1lIjoianV5b3VuZyIsImVtYWlsIjoianV5b3VuZ0BlbWFpbC5jb20iLCJpYXQiOjE1NTg1OTg3NzYsImV4cCI6MTU1ODYwMjM3Nn0.KCJ8UDgMbA4a6GSoqB_ECEMT7g4wJn3vshZjy5G-zbc";
        jwtTokenProvider.validateToken(nonToken);
    }

    @Test
    public void jwtToken_validate_decode() {
        Account expected = getAccount();
        String jwtToken = jwtTokenProvider.createToken(getSecurityUser());

        given(accessTokenRepository.save(any())).willReturn(null);
        Claims result = jwtTokenProvider.decode(jwtToken);

        System.out.println(result);

        assertThat(result.get("email")).isEqualTo(expected.getEmail());
    }

    private String createJwtToken(Account account){
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expirationInMs);

        return Jwts.builder()
                .setSubject(String.valueOf(account.getUsername()))
                .signWith(JWT_KEY)
                .claim("id", account.getId())
                .claim("username", account.getUsername())
                .claim("email", account.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .compact();
    }
}