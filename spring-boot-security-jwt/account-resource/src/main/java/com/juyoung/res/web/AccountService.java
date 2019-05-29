package com.juyoung.res.web;

import com.juyoung.res.web.domain.Account;
import com.juyoung.res.web.exception.AccountNotFoundException;
import com.juyoung.res.web.model.AccountUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Account updateMyAccount(final long id,
                                   AccountUpdateRequest accountUpdateRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        account.updateAccount(accountUpdateRequest);
        return accountRepository.save(account);
    }

    public Account search(final long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account deleteAccount(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        account.deleteAccount();
        return accountRepository.save(account);
    }
}