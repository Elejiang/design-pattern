package com.example.designpattern.interpreter;

import java.util.HashMap;
import java.util.Map;

class Context {
    private Map<String, Integer> map = new HashMap<>();
    public int get(String name) {
        return map.get(name);
    }
    public void set(String name, int value) {
        map.put(name, value);
    }
}
