package com.example.designpattern.visitor;

class Owner implements Person {

    @Override
    public void feedCat(Cat cat) {
        System.out.println("主人喂猫，并说了声，我家猫可爱吧");
    }

    @Override
    public void feedDog(Dog dog) {
        System.out.println("主人喂狗，并说了声，我家狗可爱吧");
    }
}
