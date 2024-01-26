package com.example.designpattern.command;

import java.util.ArrayList;
import java.util.List;

class Waiter {
    private List<Command> orders = new ArrayList<>();
    public void addOrder(Command command) {
        orders.add(command);
        System.out.println("新增订单：" + command.information);
    }
    public void cancelOrder(Command command) {
        orders.remove(command);
        System.out.println("取消订单：" + command.information);
    }
    public void executeOrders() {
        for (Command command : orders) {
            command.executeCommand();
        }
    }
}
