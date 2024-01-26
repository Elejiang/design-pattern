package com.example.designpattern.visitor;

class Dog implements Animal {
    @Override
    public void accept(Person person) {
        person.feedDog(this);
        System.out.println("好好吃，汪汪汪！！！");
    }
}
