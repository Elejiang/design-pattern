package com.example.designpattern.singleton;

/**
 * 双检锁线程安全的懒汉式单例
 */
class DCLLazySingleton {
    private static volatile DCLLazySingleton instance;
    private DCLLazySingleton(){}
    public static DCLLazySingleton getInstance() {
        if (instance == null) {
            synchronized (DCLLazySingleton.class) {
                if (instance == null) {
                    instance = new DCLLazySingleton();
                }
            }
        }
        return instance;
    }
}
