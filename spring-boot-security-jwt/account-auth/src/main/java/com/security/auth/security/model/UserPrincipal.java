package com.security.auth.security.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.auth.domain.Account;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean state;

    public static UserPrincipal of(Account account) {
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + account.getRoleType().name()));
        return new UserPrincipal(
                account.getId(),
                account.getEmail(),
                account.getPassword(),
                account.getName(),
                authorities,
                account.isState()
        );
    }

    @Builder
    public UserPrincipal(Long id,
                         String email,
                         String password,
                         String name,
                         Collection<? extends GrantedAuthority> authorities,
                         boolean state) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
        this.state = state;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state;
    }
}
