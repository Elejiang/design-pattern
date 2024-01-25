package com.example.designpattern.bridge;

class Game implements Software {
    @Override
    public void run() {
        System.out.println("game run");
    }
}
