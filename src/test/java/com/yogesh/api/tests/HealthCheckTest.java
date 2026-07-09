package com.yogesh.api.tests;

import com.yogesh.api.core.RequestSpecificationFactory;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest {


    @Test
    public void shouldReturnHealthStatus() {
        RequestSpecification requestSpecification = RequestSpecificationFactory.getRequestSpecification();

        given()
                .spec(requestSpecification)
                .when().get("/ping")
                .then().statusCode(201);

    }

}
