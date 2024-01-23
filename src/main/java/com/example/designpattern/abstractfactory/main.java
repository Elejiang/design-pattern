package com.example.designpattern.abstractfactory;

class main {
    public static void main(String[] args) {
        // 切换这句代码就可以切换一套产品，由A公司切换到B公司
        Factory factory = new AFactory();
//        Factory factory = new BFactory();

        Car car = factory.createCar();
        Bike bike = factory.createBike();
        car.drive();
        bike.ride();
    }
}
