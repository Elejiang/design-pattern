package com.example.designpattern.command;

class MeatCommand extends Command{
    public MeatCommand(Cook receiver, String information) {
        super(receiver, information);
    }

    @Override
    public void executeCommand() {
        receiver.cookMeat(information);
    }
}
