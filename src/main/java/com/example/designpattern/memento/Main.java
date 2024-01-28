package com.example.designpattern.memento;

class Main {
    public static void main(String[] args) {
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();
        originator.setState("State 1");
        caretaker.add(originator.saveToMemento());
        originator.setState("State 2");
        caretaker.add(originator.saveToMemento());
        originator.setState("State 3");
        originator.getStateFromMemento(caretaker.get(0));
        System.out.println(originator.getState());
        originator.getStateFromMemento(caretaker.get(1));
        System.out.println(originator.getState());
    }
}
