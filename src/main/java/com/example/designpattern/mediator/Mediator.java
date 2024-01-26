package com.example.designpattern.mediator;

interface Mediator {
    void register(Colleague colleague); // 客户注册

    void relay(String from, String to,String ad); // 转发
}
