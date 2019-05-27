package com.security.auth.controller;

import com.security.auth.domain.Account;
import com.security.auth.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account getAccount(@PathVariable final long id) {
        return customUserDetailsService.search(id);
    }

}
