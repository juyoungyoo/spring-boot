package com.security.auth.controller;

import com.security.auth.domain.Account;
import com.security.auth.model.SignUpRequest;
import com.security.auth.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AccountController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    DefaultTokenServices defaultTokenServices;


    @ResponseBody
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody SignUpRequest signUpRequest) {
        return customUserDetailsService.signUp(signUpRequest);
    }

//    }

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
