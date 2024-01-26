package com.example.designpattern.memento;

class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento saveToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(IMemento memento) {
        state = ((Memento) memento).getState();
    }

}
