package com.example.designpattern.singleton;

/**
 * 线程安全的懒汉式单例
 */
class SafeThreadLazySingleton {
    private static SafeThreadLazySingleton instance;
    private SafeThreadLazySingleton(){}
    public static SafeThreadLazySingleton getInstance() {
        synchronized (SafeThreadLazySingleton.class) {
            if (instance == null) {
                instance = new SafeThreadLazySingleton();
            }
        }
        return instance;
    }
}
