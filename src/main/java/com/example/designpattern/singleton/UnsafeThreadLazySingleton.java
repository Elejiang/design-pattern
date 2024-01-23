package com.example.designpattern.singleton;

/**
 * 线程不安全的懒汉式单例
 */
class UnsafeThreadLazySingleton {
    private static UnsafeThreadLazySingleton instance;
    private UnsafeThreadLazySingleton(){}
    public static UnsafeThreadLazySingleton getInstance() {
        if (instance == null) {
            instance = new UnsafeThreadLazySingleton();
        }
        return instance;
    }
}
