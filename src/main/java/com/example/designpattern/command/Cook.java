package com.example.designpattern.command;

class Cook {
    public void cookVegetables(String vegetable) {
        System.out.println("炒蔬菜：" + vegetable);
    }

    public void cookMeat(String meat) {
        System.out.println("炒肉：" + meat);
    }
}
