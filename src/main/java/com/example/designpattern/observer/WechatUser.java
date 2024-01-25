package com.example.designpattern.observer;

class WechatUser implements Observer {
    private String name;
    public WechatUser(String name) {
        this.name = name;
    }
    @Override
    public void update(String message) {
        System.out.println("微信用户" + name + "收到一条信息：" + message);
    }
}
