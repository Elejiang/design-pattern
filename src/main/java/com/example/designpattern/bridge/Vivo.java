package com.example.designpattern.bridge;

class Vivo extends Phone {
    @Override
    public void run() {
        System.out.print("Vivo:");
        software.run();
    }
}
