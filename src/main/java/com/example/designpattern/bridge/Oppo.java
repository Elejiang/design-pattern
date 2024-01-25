package com.example.designpattern.bridge;

class Oppo extends Phone {
    @Override
    public void run() {
        System.out.print("Oppo:");
        software.run();
    }
}
