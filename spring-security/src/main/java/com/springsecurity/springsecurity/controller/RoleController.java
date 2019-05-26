package com.springsecurity.springsecurity.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {


    @GetMapping("/user")
    @PostAuthorize("hasRole('ROLE_USER')")
    public String user(Authentication authentication) {
        System.out.println("RoleController : " + authentication.getAuthorities().toString());
        System.out.println("RoleController : " + authentication.getPrincipal());
        return "I'm Jwt Token User!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(Authentication authentication) {
        System.out.println("RoleController : " + authentication.getAuthorities().toString());
        System.out.println("RoleController : " + authentication.getPrincipal());
        return "I'm Jwt Token Admin!";
    }
}
