package com.yogesh.api.core;

import java.time.Duration;
import java.util.Set;

public final class RetryPolicy {

    private final int maxAttempts;
    private final Set<Integer> retryableStatusCodes;
    private final Duration initialBackoff;

    public RetryPolicy(
            int maxAttempts,
            Set<Integer> retryableStatusCodes,
            Duration initialBackoff) {

        this.maxAttempts = maxAttempts;
        this.retryableStatusCodes = retryableStatusCodes;
        this.initialBackoff = initialBackoff;
    }

    public boolean shouldRetry(int statusCode) {
        return retryableStatusCodes.contains(statusCode);
    }

    public Duration getBackoffDuration(int retryNumber) {
        return initialBackoff.multipliedBy(1L << (retryNumber - 1));
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
