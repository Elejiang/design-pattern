package com.example.designpattern.builder;

class Computer {
    private String audio;
    private String keyboard;
    private String master;
    private String mouse;
    private String screen;

    public Computer() {
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "audio='" + audio + '\'' +
                ", keyboard='" + keyboard + '\'' +
                ", master='" + master + '\'' +
                ", mouse='" + mouse + '\'' +
                ", screen='" + screen + '\'' +
                '}';
    }
}
