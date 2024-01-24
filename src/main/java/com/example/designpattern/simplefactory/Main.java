package com.example.designpattern.simplefactory;

class Main {
    public static void main(String[] args) {
        // 只需要切换这一句代码，就可以把下面的car具体实例全部换了
        Car car = SimpleCarFactory.getCar("A");
//        Car car = SimpleCarFactory.getCar("B");

        car.drive();
    }
}
