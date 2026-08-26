package com.yogesh.api.validation;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class ResponseSchemaValidator {

    private ResponseSchemaValidator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static void validate(
            Response response,
            String schemaPath){

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
