package com.security.auth.controller;


import com.security.auth.domain.Account;
import com.security.auth.security.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/api/users")
    public List<Account> accounts(){
        return accountService.findAll();
    }
}
