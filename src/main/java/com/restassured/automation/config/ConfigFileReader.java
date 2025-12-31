package com.restassured.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private Properties properties;
    private String propertyFilePath = "src/main/resources/config.properties";
    
    public ConfigFileReader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(propertyFilePath);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getBaseURI() {
        String baseURI = properties.getProperty("base_uri");
        if (baseURI != null) {
            return baseURI;
        } else {
            throw new RuntimeException("base_uri not specified in config.properties file");
        }
    }
    
    public String getContentType() {
        return properties.getProperty("content_type", "application/json");
    }
    
    public String getTimeout() {
        return properties.getProperty("timeout", "10");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
