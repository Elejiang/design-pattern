package com.example.designpattern.strategy;

class Main {
    public static void main(String[] args) {
        Mall mall = new Mall();
        mall.setStrategy(new StrategyA());
        mall.mallShow();
        mall.setStrategy(new StrategyB());
        mall.mallShow();
        mall.setStrategy(new StrategyC());
        mall.mallShow();
    }
}
