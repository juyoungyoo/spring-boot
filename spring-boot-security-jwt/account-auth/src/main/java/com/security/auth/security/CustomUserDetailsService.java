package com.security.auth.security;


import com.security.auth.common.AppProperties;
import com.security.auth.domain.Account;
import com.security.auth.exception.AccountNotFoundException;
import com.security.auth.exception.EmailDuplicationException;
import com.security.auth.model.AccountUpdateRequest;
import com.security.auth.model.SignUpRequest;
import com.security.auth.repository.AccountRepository;
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
        signUpRequest.encodePassword(passwordEncoder);
        return accountRepository.save(signUpRequest.toEntity());
    }

    public Account updateMyAccount(final long id,
                                   AccountUpdateRequest accountUpdateRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        account.updateMyAccount(accountUpdateRequest);
        return accountRepository.save(account);
    }

    public Account search(final long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        return UserPrincipal.of(account);
    }

}