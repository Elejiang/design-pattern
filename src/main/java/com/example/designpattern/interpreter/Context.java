package com.example.designpattern.interpreter;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

class Context {
    private StringBuilder result = new StringBuilder();

    private HashMap<String, String> map = new HashMap<>();

    public Context() {
        loadMap();
    }

    public String getValue(String key) {
        return map.get(key);
    }

    public StringBuilder getResult() {
        return result;
    }

    public void loadMap() {
        Properties prop = new Properties();
        try (InputStream input = Context.class.getClassLoader().getResourceAsStream("interpreter.properties")) {
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            prop.load(reader);
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                map.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
