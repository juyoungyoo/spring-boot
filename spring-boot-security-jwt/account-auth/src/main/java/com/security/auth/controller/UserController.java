package com.security.auth.controller;

import com.security.auth.domain.Account;
import com.security.auth.model.AccountUpdateRequest;
import com.security.auth.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable final long id) {
        return customUserDetailsService.search(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccount(@PathVariable final long id,
                                 @RequestBody final AccountUpdateRequest accountUpdateRequest) {
        return customUserDetailsService.updateMyAccount(id, accountUpdateRequest);
    }

}
