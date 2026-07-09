package com.yogesh.api.config;

import com.yogesh.api.exceptions.InvalidEnvironmentException;

import java.util.Arrays;

public enum Environment {

    LOCAL,
    QA,
    STAGE,
    UAT,
    PROD;

    public static Environment from(String value) {

        if (value == null || value.isBlank()) {
            throw new InvalidEnvironmentException(
                    "Environment cannot be null or blank.");
        }

        try {
            return Environment.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnvironmentException(
                    "Unsupported environment: " + value);
        }
    }
}
