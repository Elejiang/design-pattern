package com.example.designpattern.adapter;

class SDCardImpl implements SDCard {
    @Override
    public String readSD() {
        return "read SD card";
    }
    @Override
    public void writeSD(String msg) {
        System.out.println("write SD card:" + msg);
    }
}
