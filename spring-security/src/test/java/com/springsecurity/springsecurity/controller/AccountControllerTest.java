package com.springsecurity.springsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecurity.springsecurity.AbstractTest;
import com.springsecurity.springsecurity.domain.RoleType;
import com.springsecurity.springsecurity.model.SignInRequest;
import com.springsecurity.springsecurity.model.SignUpRequest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTest extends AbstractTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void signUp_success() throws Exception {
        SignUpRequest account = SignUpRequest.builder()
                .username("juyoung")
                .email("test@gmail.com")
                .password("password")
                .role(RoleType.USER)
                .build();

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(Matchers.not(0)))
                .andExpect(jsonPath("username").value(USER_NAME))
        ;
    }

    @Test
    public void signIn_success() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}