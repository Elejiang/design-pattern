package com.example.designpattern.singleton;

/**
 * 饿汉式单例
 */
class EagerSingleton {
    private static EagerSingleton instance = new EagerSingleton();
    private EagerSingleton(){}
    public static EagerSingleton getInstance() {
        return instance;
    }
}
