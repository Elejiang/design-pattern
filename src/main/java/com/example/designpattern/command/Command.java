package com.example.designpattern.command;

abstract class Command {
    protected Cook receiver;
    protected String information;
    public Command(Cook receiver, String information) {
        this.receiver = receiver;
        this.information = information;
    }
    abstract public void executeCommand();
}
