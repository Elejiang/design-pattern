package com.example.designpattern.observer;

class QQUser implements Observer {
    private String name;
    public QQUser(String name) {
        this.name = name;
    }
    @Override
    public void update(String message) {
        System.out.println("QQ用户" + name + "收到一条信息：" + message);
    }
}
