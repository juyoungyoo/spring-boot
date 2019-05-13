package com.security.auth.controller;


import com.security.auth.domain.Account;
import com.security.auth.security.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/account/all")
    public String getAccounts(){
        return "success";
    }

    @GetMapping("/account")
    public Account getAccount(@RequestParam String username) {
        return accountService.search(username);
    }

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessage(@RequestBody Account account) {
        accountService.save(account);
    }
}
