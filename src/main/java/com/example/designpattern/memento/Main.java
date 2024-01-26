package com.example.designpattern.memento;

class Main {
    public static void main(String[] args) {
        CareTaker careTaker = new CareTaker();
        Originator originator = new Originator();
        originator.setState("State 1");
        careTaker.add(originator.saveToMemento());
        originator.setState("State 2");
        careTaker.add(originator.saveToMemento());
        originator.setState("State 3");
        originator.getStateFromMemento(careTaker.get(0));
        System.out.println(originator.getState());
        originator.getStateFromMemento(careTaker.get(1));
        System.out.println(originator.getState());
    }
}
