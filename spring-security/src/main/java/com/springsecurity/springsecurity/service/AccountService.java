package com.springsecurity.springsecurity.service;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.error.exceptions.AccountNotFoundException;
import com.springsecurity.springsecurity.model.SignInRequest;
import com.springsecurity.springsecurity.model.SignUpRequest;
import com.springsecurity.springsecurity.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Account signUp(SignUpRequest signUpRequest) {
        Optional<Account> existAccount = accountRepository.findByEmail(signUpRequest.getEmail());
        if (existAccount.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 계정입니다");
        }

        signUpRequest.encodePassword(passwordEncoder);
        Account account = signUpRequest.toEntity();
        return accountRepository.save(account);
    }

    public Account findAccount(String email) {
        return  accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("계정을 찾을 수 없습니다. [email : " + email + "]"));
    }

    public Account signIn(SignInRequest signInRequest) {
        Account account = accountRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Not found account ::" + signInRequest.getEmail()));
        return account;
    }
}