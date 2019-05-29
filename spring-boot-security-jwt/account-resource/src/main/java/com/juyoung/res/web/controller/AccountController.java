package com.juyoung.res.web.controller;

import com.juyoung.res.web.AccountService;
import com.juyoung.res.web.domain.Account;
import com.juyoung.res.web.model.AccountUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users")
@RestController
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable final long id) {
        return accountService.search(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccount(@PathVariable final long id,
                                 @RequestBody final AccountUpdateRequest accountUpdateRequest) {
        return accountService.updateMyAccount(id, accountUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable final long id) {
        accountService.deleteAccount(id);
    }

}
