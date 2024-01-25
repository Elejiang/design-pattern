package com.example.designpattern.strategy;

class Mall {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void mallShow() {
        strategy.show();
    }
}
