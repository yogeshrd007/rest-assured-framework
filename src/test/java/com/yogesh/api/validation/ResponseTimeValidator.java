package com.yogesh.api.validation;

import com.yogesh.api.config.ConfigManager;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.Duration;

public final class ResponseTimeValidator {

    private ResponseTimeValidator() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static void verifyResponseTimeLessThan(
            Response response) {

        Duration expectedDuration =
                Duration.ofSeconds(
                        ConfigManager.getResponseTimeThreshold()
                );

        long actualTime = response.getTime();


        Assert.assertTrue(
                actualTime < expectedDuration.toMillis(),
                "Response time exceeded limit. Expected: "
                        + expectedDuration
                        + ", Actual: "
                        + actualTime
                        + " ms"
        );

    }

    public static void verifyResponseTimeLessThan(
            Response response,
            Duration expectedDuration) {

        long actualTime = response.getTime();
        long expectedTime = expectedDuration.toMillis();

        Assert.assertTrue(
                actualTime < expectedTime,
                "Response time exceeded expected limit. Expected: "
                        + expectedDuration
                        + ", Actual: "
                        + actualTime
                        + " ms"
        );
    }
}
