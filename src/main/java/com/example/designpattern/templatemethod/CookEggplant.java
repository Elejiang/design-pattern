package com.example.designpattern.templatemethod;

class CookEggplant extends CookTemplate{
    @Override
    public void pourVegetable() {
        System.out.println("下锅的蔬菜是茄子");
    }

    @Override
    public void pourSauce() {
        System.out.println("下锅的酱料是蒜蓉");
    }
}
