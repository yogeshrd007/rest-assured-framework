package com.yogesh.api.core;

import com.yogesh.api.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecificationFactory {

    private RequestSpecificationFactory(){
        throw new AssertionError("Utility class should not be instantiated.");
    }

    public static RequestSpecification getRequestSpecification(){
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept("*/*")
                .build();
    }
}
