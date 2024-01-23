package com.example.designpattern.factorymethod;

class main {
    public static void main(String[] args) {
        // 只需要切换这一句代码，就可以把下面的car具体实例全部换了
        CarFactory carFactory = new ACarFactory();
//        CarFactory carFactory = new BCarFactory();

        Car car = carFactory.createCar();
        car.drive();
    }
}
