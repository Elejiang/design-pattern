package com.example.designpattern.adapter;

class Computer {
    public String readSD(SDCard sdCard) {
        return sdCard.readSD();
    }
    public void writeSD(SDCard sdCard, String msg) {
        sdCard.writeSD(msg);
    }
}
