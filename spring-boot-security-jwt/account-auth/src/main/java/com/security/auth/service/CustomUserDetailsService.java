package com.security.auth.service;

import com.security.auth.domain.Account;
import com.security.auth.domain.RoleType;
import com.security.auth.exception.AccountNotFoundException;
import com.security.auth.exception.EmailDuplicationException;
import com.security.auth.model.SignUpRequest;
import com.security.auth.repository.AccountRepository;
import com.security.auth.security.AppProperties;
import com.security.auth.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AppProperties appProperties;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Account signUp(SignUpRequest signUpRequest) {
        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailDuplicationException(signUpRequest.getEmail());
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return accountRepository.save(signUpRequest.toEntity());
    }

    public void fexture(){
        setFixtureAccount("admin", appProperties.getAdminId(), appProperties.getAdminPassword(), RoleType.MANAGER);
        setFixtureAccount("user", appProperties.getUserId(), appProperties.getUserPassword(), RoleType.USER);
    }

    private void setFixtureAccount(String name,
                                   String email,
                                   String password,
                                   RoleType roleType) {
        SignUpRequest account = SignUpRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .roleType(roleType)
                .emailVerified(true)
                .build();
        try {
            loadUserByUsername(email);
        } catch (Exception e) {
            signUp(account);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        return UserPrincipal.of(account);
    }

}