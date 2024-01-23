package com.example.designpattern.factorymethod;

class BCarFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new BCar();
    }
}
