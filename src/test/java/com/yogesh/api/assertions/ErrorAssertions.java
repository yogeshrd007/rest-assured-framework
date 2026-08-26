package com.yogesh.api.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public final class ErrorAssertions {

    private ErrorAssertions() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static void verifyErrorResponse(
            Response response,
            int expectedStatusCode,
            String expectedContentType) {

        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Unexpected error status code"
        );

        Assert.assertTrue(
                response.getContentType().contains(expectedContentType),
                "Unexpected error content type. Expected: "
                        + expectedContentType
                        + ", Actual: "
                        + response.getContentType()
        );
    }
}
