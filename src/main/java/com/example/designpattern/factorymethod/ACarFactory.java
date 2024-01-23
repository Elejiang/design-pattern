package com.example.designpattern.factorymethod;

class ACarFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new ACar();
    }
}
