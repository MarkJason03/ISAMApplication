package com.example.fyp_application.Utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesHandler {
    private static final Properties properties = new Properties();



    static {
        try (InputStream inputStream = ConfigPropertiesHandler.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("No application.application.properties file found in the classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load application application.properties", e);
        }
    }

    public static String getValue(String key) {
        String value = properties.getProperty(key);
        System.out.println("Value for " + key + " is " + value);

        if (value == null) {
            System.err.println("Warning: Property for " + key + " not found");
        }
        return value;
    }


}
