package com.security.auth.controller;

import com.security.auth.domain.Account;
import com.security.auth.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account getAccount(@PathVariable final long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("No Account"));
        return account;
    }
}
