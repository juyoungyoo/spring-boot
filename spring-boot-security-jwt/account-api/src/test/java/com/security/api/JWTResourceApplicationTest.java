package com.security.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RESTResourceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JWTResourceApplicationTest {

    @Autowired
    private JwtTokenStore tokenStore;

    @Test
    public void whenTokenDoesNotContainIssuer_thenSuccess() {
        String tokenValue = obtainAccessToken("juyoung", "pass");
        OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        Map<String, Object> details = (Map<String, Object>) auth.getDetails();

        assertTrue(details.containsKey("organization"));
    }

    private String obtainAccessToken(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);

        Response response = RestAssured.given()
                .auth().preemptive().basic("juyoung-client", "juyoung-password")
                .and().with().params(params)
                .when().post("http://localhost:8081/oauth/token");
        return response.jsonPath().getString("access_token");
    }

}
