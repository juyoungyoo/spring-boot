package com.juyoung.res.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juyoung.res.web.model.AccountUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private static final String TEST_USER_ID = "user@gmail.com";
    private static final String TEST_USER_PASSWORD = "password";
    private static final String AUTH_OBTAIN_TOKEN_URL = "http://localhost:8081/auth/oauth/token";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void searchAccountById() throws Exception {
        long id = 1L;
        String email = TEST_USER_ID;

        mockMvc.perform(get("/users/" + id)
                .header(HttpHeaders.AUTHORIZATION, getBearer())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(email))
        ;
    }

    // todo : principal 사용법
    @Test
    public void getMyAccountIfo() throws Exception {
        String expectedEmail = TEST_USER_ID;

        mockMvc.perform(get("/users/me")
                .header(HttpHeaders.AUTHORIZATION, getBearer())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void updateUserInfo() throws Exception {
        long id = 1L;
        String expectedName = "change the name";

        AccountUpdateRequest accountUpdateRequest = AccountUpdateRequest.builder()
                .name(expectedName)
                .build();

        mockMvc.perform(put("/users/" + id)
                .header(HttpHeaders.AUTHORIZATION, getBearer())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountUpdateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(expectedName))
        ;
    }

    @Test
    public void deleteUser() throws Exception {
        long id = 1L;
        mockMvc.perform(delete("/users/" + id)
                .header(HttpHeaders.AUTHORIZATION, getBearer())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
        ;
    }

    private String getBearer() {
        return "Bearer " + obtainAccessToken();
    }

    private String obtainAccessToken() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("username", TEST_USER_ID);
        params.put("password", TEST_USER_PASSWORD);
        final Response response = RestAssured
                .given().auth()
                .preemptive()
                .basic("myApp", "password")
                .and()
                .with()
                .params(params).when()
                .post(AUTH_OBTAIN_TOKEN_URL);
        return response.jsonPath().getString("access_token");
    }
}