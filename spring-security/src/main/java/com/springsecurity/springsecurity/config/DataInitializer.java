package com.springsecurity.springsecurity.config;

import com.springsecurity.springsecurity.domain.Account;
import com.springsecurity.springsecurity.domain.RoleType;
import com.springsecurity.springsecurity.model.SignUpRequest;
import com.springsecurity.springsecurity.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("initializing user data...");

        SignUpRequest user = SignUpRequest.builder()
                .username("juyoung")
                .email("juyoung@gmail.com")
                .role(RoleType.USER)
                .password("pass")
                .build();

        Account account;
        try{
            account = accountService.signUp(user);
        }catch (Exception e){
            account = accountService.findAccount(user.getEmail());
        }
        log.debug("printing insert user");
        log.debug("register fixture user : " + account.toString());
    }
}

