package com.example.designpattern.state;

class RunningState extends LiftState {

    @Override
    public void open() {
        System.out.println("电梯处于运行状态，无法开启");
    }

    @Override
    public void close() {
        System.out.println("电梯处于运行状态，不需要关闭");
    }

    @Override
    public void run() {
        System.out.println("电梯已处于运行状态...");
    }

    @Override
    public void stop() {
        System.out.println("电梯停止...");
        super.context.setLiftState(Context.stoppingState);
    }
}
