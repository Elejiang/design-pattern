package com.example.designpattern.memento;

class Memento implements IMemento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
