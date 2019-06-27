package com.inflearn.springbootmvc.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/user/create")
    public @ResponseBody User create(@RequestBody User user){
        return user;
    }
}
