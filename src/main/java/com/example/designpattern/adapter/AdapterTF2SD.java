package com.example.designpattern.adapter;

class AdapterTF2SD extends TFCardImpl implements SDCard{
    @Override
    public String readSD() {
        return readTF();
    }
    @Override
    public void writeSD(String msg) {
        writeTF(msg);
    }
}
