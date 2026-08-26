package com.yogesh.api.validation;

import io.restassured.response.Response;
import org.testng.Assert;

public final class ResponseHeaderValidator {

    private ResponseHeaderValidator() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static void verifyHeaderContains(
            Response response,
            String headerName,
            String expectedValue){

        String actualValue = response.getHeader(headerName);

        Assert.assertNotNull(
                actualValue,
                "Header not found: " + headerName
        );

        Assert.assertTrue(
                actualValue.contains(expectedValue),
                "Unexpected header value for: " + headerName
                        + ". Expected to contain: " + expectedValue
                        + ", but was: " + actualValue
        );


    }

    public static void verifyHeaderEquals(
            Response response,
            String headerName,
            String expectedValue) {

        String actualValue = response.getHeader(headerName);

        Assert.assertNotNull(
                actualValue,
                "Header not found: " + headerName
        );

        Assert.assertTrue(
                actualValue.contains(expectedValue),
                "Unexpected header value for: " + headerName
                        + ". Expected to contain: " + expectedValue
                        + ", but was: " + actualValue
        );


    }
}
