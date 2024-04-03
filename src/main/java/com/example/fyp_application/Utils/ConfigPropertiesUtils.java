package com.example.fyp_application.Utils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigPropertiesUtils {
    private static final Properties properties = new Properties();


    // Load the application.application.properties file
    static {
        try (InputStream inputStream = ConfigPropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                // If the file is not found, throw an exception
                throw new IllegalStateException("No application.application.properties file found in the classpath");
            }
            // Load the properties from the file
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load application application.properties", e);
        }
    }

    public static String getPropertyValue(String key) {

        // Get the value of the key from the properties
        String value = properties.getProperty(key);


        if (value == null) {
            System.err.println("Warning: Property for " + key + " not found");
        }
        return value;
    }


    public static void setPropertyValue(String key, String value) {
        properties.setProperty(key, value);
        try (OutputStream output = new FileOutputStream("src/main/resources/config.properties")) {
            properties.store(output, null);
        } catch (IOException io) {
            throw new RuntimeException("Could not write to config.properties", io);
        }
    }

}
