package com.example.designpattern.decorator;

abstract class Garnish extends FastFood {
    private FastFood fastFood;
    public FastFood getFastFood() {
        return fastFood;
    }
    public void setFastFood(FastFood fastFood) {
        this.fastFood = fastFood;
    }
    public Garnish(FastFood fastFood, float price, String desc) {
        super(price,desc);
        this.fastFood = fastFood;
    }
}
