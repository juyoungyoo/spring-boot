package com.juyoung.res.web.controller;


import com.juyoung.res.web.model.Foo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Controller
public class RoleController {

    public RoleController() {
        super();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @PreAuthorize("#oauth2.hasScope('read') and #oauth2.hasScope('write')")
    public Foo findById(@PathVariable final long id) {
        return new Foo(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    public String create(@RequestBody final Foo foo) {
        return "write connected";
    }
}
