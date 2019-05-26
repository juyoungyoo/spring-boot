package com.springsecurity.springsecurity.security.login;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.error.exceptions.AccountNotFoundException;
import com.springsecurity.springsecurity.repository.AccountRepository;
import com.springsecurity.springsecurity.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Not found account ::" + email));
        return new SecurityUser(account);
    }
}