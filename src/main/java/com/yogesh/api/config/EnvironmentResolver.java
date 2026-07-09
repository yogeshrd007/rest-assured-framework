package com.yogesh.api.config;

import com.yogesh.api.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EnvironmentResolver {

    private static final String ENV_PROPERTY = "env";
    private static final String APPLICATION_PROPERTIES = "config/application.properties";

    private EnvironmentResolver (){
        throw new AssertionError("utility class should not be instantiated");
    }

    public static Environment resolve(){

        //Priority 1: JVM property
        String env = System.getProperty(ENV_PROPERTY);

        if(env!=null && !env.isBlank()){
            return Environment.from(env);
        }

        //priority 2: application.properties
        Properties properties= PropertyLoader.loadProperties(APPLICATION_PROPERTIES);

        String defaultEnvironment = properties.getProperty("environment");
        if(defaultEnvironment==null || defaultEnvironment.isBlank()){
            throw  new InvalidConfigurationException("property 'environment' not found in application.properties");
        }
        return Environment.from(defaultEnvironment);
    }


    }
