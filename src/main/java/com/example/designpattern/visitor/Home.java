package com.example.designpattern.visitor;

import java.util.ArrayList;
import java.util.List;

class Home {
    private List<Animal> animals = new ArrayList<>();

    // 当前访问者对每个节点进行访问
    public void action(Person person) {
        for (Animal animal : animals) {
            animal.accept(person);
        }
    }

    public void add(Animal animal) {
        animals.add(animal);
    }
}
