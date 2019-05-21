package com.security.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RESTResourceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceServerIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {

    }

    @Test
    public void givenUser_When_Use_FooClient_then_Ok_For_FooResource_Only() {


        final String accessToken = obtainAccessToken("foo", "juyoung", "secret");

        final Response fooResponse = RestAssured.given()
                                    .header("Authorization", "Bearer " + accessToken)
                                    .get("http://localhost:8080/foo/1");

        assertEquals(200, fooResponse.getStatusCode());
        assertNotNull(fooResponse.jsonPath().get("name"));

//        final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
//                                    .get("http://localhost:8080/bars/1");
//        assertEquals(403, barResponse.getStatusCode());
    }

    private String obtainAccessToken(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String clientSecret = passwordEncoder.encode("secret");

        final Response response = RestAssured.given().auth()
                .preemptive().basic(clientId, clientSecret)
                .and()
                    .with().params(params)
                .when().post("http://localhost:8081/oauth/token");
        return response.jsonPath().getString("access_token");
    }

}