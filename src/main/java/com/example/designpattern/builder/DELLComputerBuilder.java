package com.example.designpattern.builder;

class DELLComputerBuilder extends ComputerBuilder{
    @Override
    public void buildAudio() {
        computer.setAudio("dell音响");
    }
    @Override
    public void buildKeyboard() {
        computer.setKeyboard("dell键盘");
    }
    @Override
    public void buildMaster() {
        computer.setMaster("dell主机");
    }
    @Override
    public void buildMouse() {
        computer.setMouse("dell鼠标");
    }
    @Override
    public void buildScreen() {
        computer.setScreen("dell显示器");
    }
}
