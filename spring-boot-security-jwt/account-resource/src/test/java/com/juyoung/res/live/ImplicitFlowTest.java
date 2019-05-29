package com.juyoung.res.live;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// account-auth, account-resource server 실행 후 test code run
public class ImplicitFlowTest {
    public final static String AUTH_SERVER = "http://localhost:8081/auth";
    public final static String RESOURCE_SERVER = "http://localhost:8082/account";

    @Test
    public void givenUser_whenUseFooClient_thenOkForFooResourceOnly() {
        final String accessToken = obtainAccessToken("myAppImplicit", "user@gmail.com", "123");

        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get(RESOURCE_SERVER + "/foos/1");
        assertEquals(200, fooResponse.getStatusCode());
        assertNotNull(fooResponse.jsonPath().get("name"));
    }

    private String obtainAccessToken(String clientId,
                                     String username,
                                     String password) {
        final String redirectUrl = "http://localhost:8081/auth";

        // user login
        Response response = RestAssured.given()
                .formParams("username", username, "password", password)
                .post(AUTH_SERVER + "/login");
        final String cookieValue = response.getCookie("JSESSIONID");

        // get access token
        final Map<String, String> params = new HashMap<String, String>();
        params.put("response_type", "token");
        params.put("client_id", clientId);
        params.put("redirect_uri", redirectUrl);
        String authUrl = AUTH_SERVER + "/oauth/authorize";

        response = RestAssured.given()
                .cookie("JSESSIONID", cookieValue)
                .formParams(params)
                .post(authUrl);

        final String location = response.getHeader(HttpHeaders.LOCATION);
        System.out.println("Location => " + location);

        assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
        final String accessToken = location.split("#|=|&")[2];
        return accessToken;
    }
}
