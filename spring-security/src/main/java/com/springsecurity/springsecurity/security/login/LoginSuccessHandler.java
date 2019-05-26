package com.springsecurity.springsecurity.security.login;

import com.springsecurity.springsecurity.security.JwtTokenProvider;
import com.springsecurity.springsecurity.security.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    final String JWT_HEADER_NAME = "colini";
    private final JwtTokenProvider jwtTokenProvider;

    public LoginSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.createToken(userDetails);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        response.setHeader(JWT_HEADER_NAME, "Bearer " + accessToken);

        clearAuthenticationSession(request);
    }

    private void clearAuthenticationSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}