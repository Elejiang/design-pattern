package com.example.designpattern.mediator;

class ConcreteColleague extends Colleague{
    public ConcreteColleague(String name) {
        super(name);
    }

    @Override
    public void send(String to, String ad) {
        mediator.relay(name, to, ad);
    }

    @Override
    public void receive(String from, String ad) {
        System.out.println(name + "接收到来自" + from + "的消息:" + ad);
    }
}
