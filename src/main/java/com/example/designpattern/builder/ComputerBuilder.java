package com.example.designpattern.builder;

abstract class ComputerBuilder {
    Computer computer;
    public Computer getComputer() {
        return computer;
    }
    public void buildComputer() {computer = new Computer();};
    public abstract void buildAudio();
    public abstract void buildKeyboard();
    public abstract void buildMaster();
    public abstract void buildMouse();
    public abstract void buildScreen();
}
