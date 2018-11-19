package com.epam.cipher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    Properties properties = new Properties();

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperties(String fileName) {
        InputStream inputStream = PropertyManager.class.getClassLoader().getResourceAsStream(fileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}