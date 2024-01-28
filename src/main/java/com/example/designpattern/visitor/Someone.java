package com.example.designpattern.visitor;

class Someone implements Person {

    @Override
    public void feedCat(Cat cat) {
        System.out.println("其他人喂猫，夸了句，你家猫真听话");
    }

    @Override
    public void feedDog(Dog dog) {
        System.out.println("其他人喂狗，夸了句，你家狗好活泼");
    }
}
