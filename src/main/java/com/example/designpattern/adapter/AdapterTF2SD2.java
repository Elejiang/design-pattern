package com.example.designpattern.adapter;

class AdapterTF2SD2 implements SDCard{
    private TFCard tfCard;
    public AdapterTF2SD2(TFCard tfCard) {
        this.tfCard = tfCard;
    }
    @Override
    public String readSD() {
        return tfCard.readTF();
    }
    @Override
    public void writeSD(String msg) {
        tfCard.writeTF(msg);
    }
}
