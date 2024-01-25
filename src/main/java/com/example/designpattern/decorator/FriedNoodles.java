package com.example.designpattern.decorator;

class FriedNoodles extends FastFood {
    public FriedNoodles() {
        super(12, "炒面");
    }
    public float cost() {
        return getPrice();
    }
}
