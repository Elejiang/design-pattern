package com.example.designpattern.builder;

class Director {
    private ComputerBuilder computerBuilder;

    public Director(ComputerBuilder computerBuilder) {
        this.computerBuilder = computerBuilder;
    }

    public Computer constructComputer() {
        computerBuilder.buildComputer();
        computerBuilder.buildMaster();
        computerBuilder.buildAudio();
        computerBuilder.buildKeyboard();
        computerBuilder.buildMouse();
        computerBuilder.buildScreen();
        return computerBuilder.getComputer();
    }
}
