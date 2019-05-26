package com.springsecurity.springsecurity.security.jwt;

import com.springsecurity.springsecurity.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
public class JwtAuthenticationToken implements Authentication {

    private boolean authenticated = false;
    private String name;
    private Object credentials; // Access Token
    private Object details;     // Decode jwt token
    private Object principal;   // This object
    private Collection<? extends GrantedAuthority> authorities;
    private long userId;

    JwtAuthenticationToken(final String token) {
        credentials = token;
    }

    @Override
    public void setAuthenticated(final boolean ignore) { }

    public String getToken() {
        return (String) credentials;
    }

    public void bind(JwtTokenProvider jwtTokenProvider, String token) {
        details = jwtTokenProvider.decode(token);

        userId = getId();
        name = getName("name");
        authorities = getRole();
        principal = this;
        authenticated = true;
    }

    private List<SimpleGrantedAuthority> getRole() {
        return Arrays.asList(new SimpleGrantedAuthority((String) getClaims().get("role")));
    }

    private String getName(String name) {
        return (String) getClaims().get(name);
    }

    private long getId() {
        return Long.parseLong(getClaims().get("id").toString());
    }

    private Claims getClaims() {
        return (Claims) this.details;
    }
}