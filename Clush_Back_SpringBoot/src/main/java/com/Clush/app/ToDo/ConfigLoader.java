package com.Clush.app.ToDo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties = new Properties();

    public ConfigLoader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }

            // load a properties file from class path, inside static method
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getNaverSearchAPIId() {
        return getProperty("naverSearch.id");
    }

    public String getNaverSearchAPIKey() {
        return getProperty("naverSearch.pw");
    }


}
