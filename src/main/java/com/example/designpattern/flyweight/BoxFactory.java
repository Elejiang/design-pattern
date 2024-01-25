package com.example.designpattern.flyweight;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

class BoxFactory {
    private static HashMap<String, AbstractBox> map;
    private BoxFactory() {
        map = new HashMap<>();
        loadBoxes();
    }
    public static BoxFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        private static final BoxFactory INSTANCE = new BoxFactory();
    }
    public AbstractBox getBox(String key) {
        return map.get(key);
    }

    private void loadBoxes() {
        Properties prop = new Properties();
        try (InputStream input = BoxFactory.class.getClassLoader().getResourceAsStream("flyweightconfig.properties")) {
            prop.load(input);
            for (String key : prop.stringPropertyNames()) {
                String className = prop.getProperty(key);
                try {
                    Class<?> clazz = Class.forName(className);
                    AbstractBox box = (AbstractBox) clazz.getDeclaredConstructor().newInstance();
                    map.put(key, box);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
