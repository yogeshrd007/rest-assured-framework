package com.yogesh.api.config;

import com.yogesh.api.exceptions.InvalidConfigurationException;

import java.util.Properties;

public final class ConfigValidator {

    private ConfigValidator(){
        throw new AssertionError("Utility class should not be instantiated.");
    }

    public static void validate(Properties properties){
        validateRequiredProperty(properties, "base.url");
        validateRequiredProperty(properties, "timeout");
        validateRequiredProperty(properties, "retry.count");

        validateInteger(properties, "timeout");
        validateInteger(properties, "retry.count");
    }

    private static void validateRequiredProperty(Properties properties, String key){
        String value = properties.getProperty(key);

        if(value==null || value.isBlank()){
            throw new InvalidConfigurationException(
                    "Required property '" + key + "' is missing or empty.");
        }
    }

    private static void validateInteger(Properties properties, String key){
        String value = properties.getProperty(key);

        try{
            Integer.parseInt(value);
        }catch (NumberFormatException e){
            throw new InvalidConfigurationException(
                    "property '" + key + "' should be an integer.\nFound: " + value, e);

        }
    }
}
