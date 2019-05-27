package com.security.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.auth.common.AppProperties;
import org.junit.Before;
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
    @Autowired
    AppProperties appProperties;
    @Autowired
    ObjectMapper objectMapper;

    private String CLIENT_ID = "myApp";
    private String CLIENT_SECRET = "password";
    private String USER_NAME = "john";
    private String USER_PASSWORD = "123";

    @Before
    public void setUp() throws Exception {
        CLIENT_ID = appProperties.getClientId();
        CLIENT_SECRET = appProperties.getClientId();
    }

    @Test
    public void create_Token_Success() throws Exception {
        mockmvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", appProperties.getUserId())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("token_type").value("bearer"))
                .andExpect(jsonPath("refresh_token").isNotEmpty())
                .andExpect(jsonPath("expires_in").isNumber())
                .andExpect(jsonPath("scope").value("read write"))
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
        ResultActions perform = mockmvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", appProperties.getUserId())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password"));

        String response = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(response).get("access_token").toString();
    }
}
