/*
package com.security.auth.config.rest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    MockMvc mockmvc;

    @Test
    public void create_Token_Success() throws Exception {
        mockmvc.perform(post("/oauth/token")
                .with(httpBasic("juyoung-client", "juyoung-password"))
                .param("username", "juyoung")
                .param("password", "pass")
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("token_type").value("bearer"))
                .andExpect(jsonPath("refresh_token").isNotEmpty())
                .andExpect(jsonPath("expires_in").isNumber())
                .andExpect(jsonPath("scope").value("read write trust"))
        ;
    }

    @Test
    public void verify_conneced_access_token() throws Exception {
        mockmvc.perform(get("/account/all")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String getBearerToken() throws Exception {
        return "bearer " + obtainAccessToken();
    }

    private String obtainAccessToken() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", "juyoung");
        params.put("password", "pass");

        ResultActions perform = mockmvc.perform(post("/oauth/token")
                .with(httpBasic("juyoung-client", "juyoung-password"))
                .param("username", "juyoung")
                .param("password", "pass")
                .param("grant_type", "password"));

        String response = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(response).get("access_token").toString();
    }
}*/
