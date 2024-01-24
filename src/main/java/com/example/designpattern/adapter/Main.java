package com.example.designpattern.adapter;

class Main {
    public static void main(String[] args) {
        Computer computer = new Computer();

        SDCardImpl sdCard = new SDCardImpl();
        System.out.println(computer.readSD(sdCard));
        computer.writeSD(sdCard, "write to sd");
        //类适配器模式：
        AdapterTF2SD adapter = new AdapterTF2SD();
        System.out.println(computer.readSD(adapter));
        computer.writeSD(adapter, "write to adapter");
        //对象适配器模式：
        AdapterTF2SD2 adapter2 = new AdapterTF2SD2(new TFCardImpl());
        System.out.println(computer.readSD(adapter2));
        computer.writeSD(adapter2, "write to adapter2");
    }
}
