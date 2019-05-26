package com.springsecurity.springsecurity.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecurity.springsecurity.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class LoginProcessFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;

    public LoginProcessFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Account account = objectMapper.readValue(request.getReader(), Account.class);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword());
        return getAuthenticationManager().authenticate(authentication);
    }

}