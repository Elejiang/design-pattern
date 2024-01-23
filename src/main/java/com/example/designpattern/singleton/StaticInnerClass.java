package com.example.designpattern.singleton;

/**
 * 静态内部类加载单例，访问StaticInnerClass时不会加载单例，调用getInstance()时才会加载
 * 且保证线程安全与高性能
 */
class StaticInnerClass {
    private StaticInnerClass(){}
    private static class StaticInnerClassHolder {
        private static final StaticInnerClass INSTANCE = new StaticInnerClass();
    }
    public static StaticInnerClass getInstance() {
        return StaticInnerClassHolder.INSTANCE;
    }
}
