package com.yogesh.api.config;

import java.util.Properties;

public final class ConfigManager {
    private static final Properties PROPERTIES;

    static {
        Environment environment = EnvironmentResolver.resolve();
        PROPERTIES = PropertyLoader.load(environment);
        ConfigValidator.validate(PROPERTIES);
    }

    private ConfigManager() {
        throw new AssertionError("Utility class should not be instantiated.");
    }

    public static String getBaseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    public static int getTimeout() {
        return getInt("timeout");
    }

    public static int getRetryCount() {
        return getInt("retry.count");
    }

    public static String getUsername() {

        String username = System.getenv("API_USERNAME");

        if(username != null && !username.isBlank()){
            return username;
        }
        return PROPERTIES.getProperty("username");
    }

    public static String getPassword() {
        String password = System.getenv("API_PASSWORD");

        if (password != null && !password.isBlank()) {
            return password;
        }

        return PROPERTIES.getProperty("password");
    }

    public static int getInt(String key) {
        return Integer.parseInt(PROPERTIES.getProperty(key));
    }

    public static int getResponseTimeThreshold() {
        return Integer.parseInt(
                PROPERTIES.getProperty("response.time.threshold")
        );
    }
}
