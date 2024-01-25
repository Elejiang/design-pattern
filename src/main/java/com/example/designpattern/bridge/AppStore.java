package com.example.designpattern.bridge;

class AppStore implements Software {
    @Override
    public void run() {
        System.out.println("Appstore run");
    }
}
