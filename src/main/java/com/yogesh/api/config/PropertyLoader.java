package com.yogesh.api.config;

import com.yogesh.api.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyLoader {

    private PropertyLoader(){
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static Properties load(Environment environment){
        Properties applicationProperties = loadProperties("config/application.properties");

        Properties environmentProperties = loadProperties("config/" +environment.name().toLowerCase() +".properties");

        applicationProperties.putAll(environmentProperties);

        return applicationProperties;

    }

    public static Properties loadProperties(String fileName){
             Properties properties = new Properties();

             try(InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(fileName)){
                 if(inputStream==null){
                     throw new InvalidConfigurationException("Property file not found: " + fileName);
                 }

                 properties.load(inputStream);
                 return properties;
             }
             catch (IOException e){
                 throw new InvalidConfigurationException("unable to load property file: " + fileName, e);
             }


    }
}
