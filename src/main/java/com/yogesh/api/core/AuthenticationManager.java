package com.yogesh.api.core;

import io.restassured.specification.RequestSpecification;

public final class AuthenticationManager {

    private AuthenticationManager(){
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static RequestSpecification getAuthenticatedRequestSpecification(){
        RequestSpecification requestSpecification= RequestSpecificationFactory.getRequestSpecification();
        requestSpecification.cookie("token", TokenManager.getToken());
        return requestSpecification;
    }
}
