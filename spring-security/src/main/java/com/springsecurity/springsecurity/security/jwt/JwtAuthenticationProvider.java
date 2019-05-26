package com.springsecurity.springsecurity.security.jwt;

import com.springsecurity.springsecurity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String accessToken = jwtAuthenticationToken.getToken();

        if (!jwtTokenProvider.verify(accessToken)) {
            throw new BadCredentialsException("Bad token");
        }

        jwtAuthenticationToken.bind(jwtTokenProvider, accessToken);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication); // 동적 확인
    }
}