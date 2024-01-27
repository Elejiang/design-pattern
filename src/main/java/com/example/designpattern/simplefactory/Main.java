package com.example.designpattern.simplefactory;

class Main {
    public static final String CAR = "A";
    public static void main(String[] args) {
        Car car1 = SimpleCarFactory.getCar(CAR);
        car1.drive();

        Car car2 = SimpleCarFactory.getCar(CAR);
        car2.drive();
    }
}
