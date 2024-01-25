package com.example.designpattern.templatemethod;

class Main {
    public static void main(String[] args) {
        CookCabbage cookCabbage = new CookCabbage();
        cookCabbage.cookProcess();
        CookEggplant cookEggplant = new CookEggplant();
        cookEggplant.cookProcess();
    }
}
