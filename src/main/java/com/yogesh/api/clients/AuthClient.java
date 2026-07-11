package com.yogesh.api.clients;

import com.yogesh.api.config.ConfigManager;
import com.yogesh.api.core.RequestSpecificationFactory;
import com.yogesh.api.core.ResponseSpecificationFactory;
import com.yogesh.api.models.AuthRequest;
import com.yogesh.api.models.AuthResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private AuthClient() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static String generateToken(){
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(ConfigManager.getUsername());
        authRequest.setPassword(ConfigManager.getPassword());

        RequestSpecification requestSpecification =
                RequestSpecificationFactory.getRequestSpecification();

        Response response =given()
                .spec(requestSpecification)
                .body(authRequest)
                .when()
                .post("/auth");

        response.then().spec(ResponseSpecificationFactory.okResponse());

        AuthResponse authResponse = response.as(AuthResponse.class);

        return authResponse.getToken();

    }
}
