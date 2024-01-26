package com.example.designpattern.visitor;

class Someone implements Person {

    @Override
    public void feedCat(Cat cat) {
        System.out.println("其他人喂猫");
    }

    @Override
    public void feedDog(Dog dog) {
        System.out.println("其他人喂狗");
    }
}
