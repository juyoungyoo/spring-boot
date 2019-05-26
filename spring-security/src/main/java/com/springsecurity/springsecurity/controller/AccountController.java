package com.springsecurity.springsecurity.controller;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.model.SignUpRequest;
import com.springsecurity.springsecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody SignUpRequest signUpRequest){
        return accountService.signUp(signUpRequest);
    }

    @GetMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public String accessCheck(){
        return "success";
    }
}
