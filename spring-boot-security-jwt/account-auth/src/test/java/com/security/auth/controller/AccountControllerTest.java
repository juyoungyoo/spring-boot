package com.security.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.auth.common.AppProperties;
import com.security.auth.domain.RoleType;
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
public class AccountControllerTest {

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



/*

    @Test
    public void get_인증_후_접근() throws Exception {
        this.mockMvc.perform(get("/users").header(HttpHeaders.AUTHORIZATION,"bearer " + getAccessToken()))
                    .andDo(print())
                    .andExpect(status().isOk())
        ;
    }


    public String getAccessToken() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/oauth/token")
                .with(httpBasic("juyoung-clienta", "juyoung-password"))
                .param("username", "juyoung")
                .param("password", "pass")
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("token_type").value("bearer"))
                .andExpect(jsonPath("refresh_token").isNotEmpty())
                .andExpect(jsonPath("scope").value("read write trust"));

        String response = resultActions.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(response).get("access_token").toString();
    }*/

}
