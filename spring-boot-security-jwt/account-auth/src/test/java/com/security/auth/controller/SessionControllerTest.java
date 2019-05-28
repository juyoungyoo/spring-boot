package com.security.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.auth.common.AppProperties;
import com.security.auth.domain.RoleType;
import com.security.auth.model.SignInRequest;
import com.security.auth.model.SignUpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
//(classes = {AuthServerConfig.class, SecurityConfig.class, CustomUserDetailsService.class, AppProperties.class})
@ActiveProfiles("test")
public class SessionControllerTest {

    @Autowired
    AppProperties appProperties;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void signIn_Success() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(appProperties.getUserId())
                .password(appProperties.getUserPassword())
                .build();

        mockMvc.perform(post(
                "/sign-in")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
        ;
    }

    @Test
    public void signUp_success() throws Exception {
        String email = "juyoung@gmail.com";
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email(email)
                .name("juyoung")
                .password("pass")
                .emailVerified(true)
                .roleType(RoleType.USER)
                .build();

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value(email))
        ;
    }

    @Test
    public void signUp_whenDuplicatedForEmail_fail() throws Exception {
        String email = appProperties.getUserId();
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email(email)
                .name("juyoung")
                .password("pass")
                .emailVerified(true)
                .roleType(RoleType.USER)
                .build();

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

}
