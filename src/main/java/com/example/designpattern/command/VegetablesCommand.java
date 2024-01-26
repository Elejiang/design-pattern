package com.example.designpattern.command;

class VegetablesCommand extends Command{
    public VegetablesCommand(Cook receiver, String information) {
        super(receiver, information);
    }

    @Override
    public void executeCommand() {
        receiver.cookVegetables(information);
    }
}
