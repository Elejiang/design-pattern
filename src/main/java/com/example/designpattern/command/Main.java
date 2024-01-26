package com.example.designpattern.command;

class Main {
    public static void main(String[] args) {
        Cook cook = new Cook();
        Command command1 = new MeatCommand(cook, "辣椒炒肉");
        Command command2 = new MeatCommand(cook, "肉沫茄子");
        Command command3 = new VegetablesCommand(cook, "耗油花菜");
        Command command4 = new VegetablesCommand(cook, "农家青菜");
        Waiter waiter = new Waiter();
        waiter.addOrder(command1);
        waiter.addOrder(command2);
        waiter.addOrder(command3);
        waiter.addOrder(command4);
        waiter.cancelOrder(command2);
        waiter.executeOrders();
    }
}
