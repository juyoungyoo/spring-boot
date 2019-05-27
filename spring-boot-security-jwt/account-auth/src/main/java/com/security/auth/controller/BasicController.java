package com.security.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class BasicController {

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Principal home(Principal principal) {
        return principal;
    }

}
