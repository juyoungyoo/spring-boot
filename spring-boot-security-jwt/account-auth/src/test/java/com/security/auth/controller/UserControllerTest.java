package com.security.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.auth.common.AppProperties;
import com.security.auth.model.AccountUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();       // todo : springSecurityFilterChain 추가할 시 mockMvc 주입을 안해도 사용이 가능하다 이유 찾기
    }

    @Test
    public void searchAccountById() throws Exception {
        long id = 1L;
        String email = appProperties.getAdminId();

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
        String expectedEmail = appProperties.getAdminId();

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
        return "bearer " + obtainAccessToken();
    }

    private String obtainAccessToken() {
        ResultActions resultActions = null;
        try {
            resultActions = mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                    .param("username", appProperties.getAdminId())
                    .param("password", appProperties.getAdminPassword())
                    .param("grant_type", "password"));
            String response = resultActions.andReturn().getResponse().getContentAsString();
            Jackson2JsonParser parser = new Jackson2JsonParser();
            return parser.parseMap(response).get("access_token").toString();
        } catch (Exception e) {
            return null;
        }
    }
}