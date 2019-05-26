package com.springsecurity.springsecurity.security;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.domain.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;

public class SecurityUser extends User {

    private Account account;

    public SecurityUser(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getRole()));
        this.account = account;
    }

    public SecurityUser(SecurityUser user) {
        super(user.getUsername(), "", user.getAuthorities());
    }

    public Account getAccount() {
        return account;
    }

    public long getId() {
        return account.getId();
    }

    public String getIdString() {
        return Long.toString(getId());
    }

    public String getName(){ return account.getUsername();}

    public String getRole() {
        return account.getRole().toString();
    }

    private static Collection<? extends GrantedAuthority> authorities(RoleType role) {
        return Arrays.asList(new SimpleGrantedAuthority(role.toString()));
    }
}
