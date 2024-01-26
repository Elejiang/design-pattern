package com.example.designpattern.mediator;

class Main {
    public static void main(String[] args) {
        Mediator mediator = new ConcreteMediator();
        Colleague colleague1 = new ConcreteColleague("张三");
        Colleague colleague2 = new ConcreteColleague("李四");
        Colleague colleague3 = new ConcreteColleague("王五");
        mediator.register(colleague1);
        mediator.register(colleague2);
        mediator.register(colleague3);

        colleague1.send("李四", "晚上出来吃饭，叫上王五");
        colleague2.send("王五", "张三叫咱一起吃饭");
        colleague3.send("张三", "李四和我说了，老地方见");
    }
}
