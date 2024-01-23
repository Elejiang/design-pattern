package com.example.designpattern.simplefactory;

class SimpleCarFactory {
    public static Car getCar(String carType) {
        if (carType.equals("A")) {
            return new ACar();
        } else if (carType.equals("B")) {
            return  new BCar();
        } else return null;
    }
}
