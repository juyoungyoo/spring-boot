package com.security.auth.security;


import com.security.auth.domain.Account;
import com.security.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @PostConstruct
    public void init(){
        Account juyoung = accountRepository.findByUsername("juyoung");
        if(juyoung == null){
            Account account = new Account();
            account.setUsername("juyoung");
            account.setPassword("pass");
            Account newAccount = this.save(account);
            System.out.println(newAccount);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Account search(String username) {
        return accountRepository.findAllByUsername(username).orElseThrow(()->new IllegalArgumentException("존재하지 않는 계정입니다.(" + username + ")"));
    }
}