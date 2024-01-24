package com.example.designpattern.adapter;

class TFCardImpl implements TFCard {
    @Override
    public String readTF() {
        return "read TF card";
    }
    @Override
    public void writeTF(String msg) {
        System.out.println("write TF card:" + msg);
    }
}
