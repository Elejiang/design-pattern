package com.example.designpattern.builder;

class HPComputerBuilder extends ComputerBuilder{
    @Override
    public void buildAudio() {
        computer.setAudio("hp音响");
    }
    @Override
    public void buildKeyboard() {
        computer.setKeyboard("hp键盘");
    }
    @Override
    public void buildMaster() {
        computer.setMaster("hp主机");
    }
    @Override
    public void buildMouse() {
        computer.setMouse("hp鼠标");
    }
    @Override
    public void buildScreen() {
        computer.setScreen("hp显示器");
    }
}
