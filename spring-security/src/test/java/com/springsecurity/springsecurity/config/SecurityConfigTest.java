package com.springsecurity.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecurity.springsecurity.model.SignInRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void passed_the_token() throws Exception {
        mockMvc.perform(get("/auth")
                .header("colini", obtainToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    private String obtainToken() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("juyoung@gmail.com")
                .password("pass")
                .build();
        ResultActions perform = mockMvc.perform(post("/sign-in")
                .content(objectMapper.writeValueAsString(signInRequest)));
        return perform.andReturn().getResponse().getHeader("colini");
    }

    @Test
    public void login_success() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("juyoung@gmail.com")
                .password("pass")
                .build();

        mockMvc.perform(post("/sign-in")
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(header().exists("colini"))
        ;
    }

    @Test
    public void login_fail_by_password_wrong() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("juyoung@gmail.com")
                .password("wrongPassword")
                .build();

        mockMvc.perform(post("/sign-in")
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }
}