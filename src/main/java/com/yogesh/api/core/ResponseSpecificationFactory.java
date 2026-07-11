package com.yogesh.api.core;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public final class ResponseSpecificationFactory {

    private static final long DEFAULT_RESPONSE_TIME_MS = 10000L;
    private ResponseSpecificationFactory(){
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static ResponseSpecification okResponse(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(DEFAULT_RESPONSE_TIME_MS))
                .build();

    }

    public static ResponseSpecification noContentResponse(){
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectResponseTime(lessThan(DEFAULT_RESPONSE_TIME_MS))
                .build();
    }
    public static ResponseSpecification createdResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectResponseTime(lessThan(DEFAULT_RESPONSE_TIME_MS))
                .build();
    }

    public static ResponseSpecification notFoundResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectResponseTime(lessThan(DEFAULT_RESPONSE_TIME_MS))
                .build();
    }

}
