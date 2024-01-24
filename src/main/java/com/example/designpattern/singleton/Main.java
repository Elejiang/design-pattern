package com.example.designpattern.singleton;

class Main {
    public static void main(String[] args) {
        UnsafeThreadLazySingleton instance1 = UnsafeThreadLazySingleton.getInstance();
        SafeThreadLazySingleton instance2 = SafeThreadLazySingleton.getInstance();
        EagerSingleton instance3 = EagerSingleton.getInstance();
        DCLLazySingleton instance4 = DCLLazySingleton.getInstance();
        StaticInnerClass instance5 = StaticInnerClass.getInstance();
        EnumSingleton instance6 = EnumSingleton.INSTANCE;
    }
}
