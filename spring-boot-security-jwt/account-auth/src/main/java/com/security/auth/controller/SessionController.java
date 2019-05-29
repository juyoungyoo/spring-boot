package com.security.auth.controller;

import com.security.auth.common.AppProperties;
import com.security.auth.domain.Account;
import com.security.auth.model.SignInRequest;
import com.security.auth.model.SignUpRequest;
import com.security.auth.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.*;

@Slf4j
@Controller
public class SessionController {

    @Autowired
    AppProperties appProperties;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    DefaultTokenServices defaultTokenServices;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    TokenStore tokenStore;

    @ResponseBody
    @PostMapping(value = "/session/sign-up", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody SignUpRequest signUpRequest) {
        return customUserDetailsService.signUp(signUpRequest);
    }

    @PostMapping(value = "/session/sign-in")
    @ResponseBody
    public OAuth2AccessToken signIn(@RequestBody SignInRequest signInRequest) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signInRequest.getEmail());
        OAuth2Request oAuth2Request = getOAuth2Request(userDetails);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);

        OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(auth);
        OAuth2AccessToken enhance = jwtAccessTokenConverter.enhance(accessToken, auth);

        return enhance;
    }

    private OAuth2Request getOAuth2Request(UserDetails userDetails) {
        Map<String, String> requestParameters = new HashMap<>();
        String clientId = appProperties.getClientId();
        boolean approved = true;
        Set<String> scope = new HashSet<>(Arrays.asList("scope"));
        Set<String> resourceIds = new HashSet<>();
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");
        Map<String, Serializable> extensionProperties = new HashMap<>();

        return new OAuth2Request(
                requestParameters,
                clientId,
                userDetails.getAuthorities(),
                approved,
                scope,
                resourceIds,
                null,
                responseTypes,
                extensionProperties);
    }
}
