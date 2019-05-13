package com.security.auth.controller;

import com.security.auth.config.jwt.AuthorizationServerConfig;
import com.security.auth.config.jwt.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthorizationServerConfig.class, SecurityConfig.class, UserDetailsService.class})
@WebAppConfiguration
public class AccountControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                        .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/employee")
                .param("username", "juyoung"))
                .andExpect(status().isUnauthorized());
    }

        @Test
    public void get_인증_후_접근() throws Exception {
        this.mockMvc.perform(get("/users")
                            .header(HttpHeaders.AUTHORIZATION,"bearer " + getAccessToken()
                            ))
                    .andDo(print())
                    .andExpect(status().isOk())
        ;
    }


    public String getAccessToken() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/oauth/token")
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
                .andExpect(jsonPath("scope").value("read write trust"));

        String response = resultActions.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(response).get("access_token").toString();
    }

}