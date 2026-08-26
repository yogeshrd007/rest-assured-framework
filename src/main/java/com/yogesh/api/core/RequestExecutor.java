package com.yogesh.api.core;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.function.Supplier;

public final class RequestExecutor {

    private static final Logger logger =
            LogManager.getLogger(RequestExecutor.class);

    private RequestExecutor() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static Response execute(Supplier<Response> responseSupplier) {

        Response response = responseSupplier.get();

        if (response.statusCode() == 401) {

            logger.warn(
                    "Received 401 Unauthorized. Invalidating cached authentication token and retrying the request once."
            );

            TokenManager.clearToken();
            response = responseSupplier.get();
        }

        return response;
    }

    public static Response execute(
            Supplier<Response> responseSupplier,
            RetryPolicy retryPolicy) {

        Response response = responseSupplier.get();

        for(int attempt =1; attempt< retryPolicy.getMaxAttempts(); attempt++) {

            if(!retryPolicy.shouldRetry(response.getStatusCode())){
                return response;
            }

            Duration backoff = retryPolicy.getBackoffDuration(attempt);

            logger.warn(
                    "Retryable response received. Status: {}. " +
                            "Retry attempt: {}/{}. Waiting: {} ms",
                    response.getStatusCode(),
                    attempt,
                    retryPolicy.getMaxAttempts()-1,
                    backoff.toMillis()
            );

            try{
                Thread.sleep(backoff.toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(
                        "Retry interuppted",
                        e
                );
            }

            response = responseSupplier.get();
            
        }
        return response;

    }
}
