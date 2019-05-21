package com.security.auth.controller;

import com.security.auth.model.SignInRequest;
import com.security.auth.security.model.JwtAuthenticationResponse;
import com.security.auth.service.CustomUserDetailsService;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/session")
public class SessionController {

//    @Autowired
//    JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    AuthenticationManager authenticationManager;
//    @Autowired
//    CustomUserDetailsService customUserDetailsService;

//    @GetMapping
//    public String loginPage(
//            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
//            @AuthenticationPrincipal OAuth2User oauth2User, Model model) {
//            model.addAttribute("userName", oauth2User.getName());
//            model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
//            model.addAttribute("userAttributes", oauth2User.getAttributes());
//        return "login";
//    }
/*
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest signInReq) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getEmail(), signInReq.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

//        String token = jwtTokenProvider.generateToken(userPrincipal);
        String token = "";

        return new JwtAuthenticationResponse(token);
    }*/

}
