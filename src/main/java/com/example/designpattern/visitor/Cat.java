package com.example.designpattern.visitor;

class Cat implements Animal {
    @Override
    public void accept(Person person) {
        person.feedCat(this);
        System.out.println("好好吃，喵喵喵！！！");
    }
}
