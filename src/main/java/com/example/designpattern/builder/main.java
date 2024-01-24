package com.example.designpattern.builder;

class main {
    public static void main(String[] args) {
        // 指挥者传入不同建造者即可建造同一复杂产品的不同组合
        Director director = new Director(new HPComputerBuilder());
//        Director director = new Director(new DELLComputerBuilder());
        Computer computer = director.constructComputer();
        System.out.println(computer.toString());
    }
}
