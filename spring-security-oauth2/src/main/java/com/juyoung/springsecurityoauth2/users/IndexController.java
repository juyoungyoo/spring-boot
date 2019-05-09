package com.juyoung.springsecurityoauth2.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "login";
    }

    @GetMapping("/session/new")
    public String index2(){
        return "/login";
    }

    @PostMapping("/session")
    public String loginProcess(){
        return "success";
    }
}
