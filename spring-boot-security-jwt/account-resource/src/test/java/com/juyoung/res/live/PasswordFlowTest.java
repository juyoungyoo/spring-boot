package com.juyoung.res.live;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PasswordFlowTest {


    @Test
    public void givenUser_whenUseFooClient_thenOkForFooResourceOnly() {
        final String accessToken = obtainAccessToken("fooClientIdPassword", "user@gmail.com", "123");

        final Response fooResponse =
                RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .get("http://localhost:8082/account/foos/1")
                ;
        assertEquals(200, fooResponse.getStatusCode());
        assertNotNull(fooResponse.jsonPath().get("name"));
    }

    @Test
    public void givenUser_whenUseBarClient_thenOkForBarResourceReadOnly() {
        final String accessToken = obtainAccessToken("myApp", "user@gmail.com", "123");

        final Response fooResponse = RestAssured
                .given()
                    .header("Authorization", "Bearer " + accessToken)
                .get("http://localhost:8082/account/foos/1")
                ;
        assertEquals(403, fooResponse.getStatusCode());
    }

    @Test
    public void givenAdmin_whenUseBarClient_thenOkForBarResourceReadWrite() {
        final String accessToken = obtainAccessToken("myApp", "admin@gmail.com", "123");

        final Response fooResponse = RestAssured
                .given()
                    .header("Authorization", "Bearer " + accessToken)
                .get("http://localhost:8082/account/foos/1");
        assertEquals(403, fooResponse.getStatusCode());

    }
    private String obtainAccessToken(String clientId,
                                     String username,
                                     String password) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given().auth().preemptive().basic(clientId, "password").and().with().params(params).when().post("http://localhost:8081/auth/oauth/token");
        return response.jsonPath().getString("access_token");
    }

}
