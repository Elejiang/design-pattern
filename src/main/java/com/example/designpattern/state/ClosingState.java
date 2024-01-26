package com.example.designpattern.state;

class ClosingState extends LiftState {
    @Override
    public void close() {
        System.out.println("电梯已处于关闭状态...");
    }

    @Override
    public void open() {
        System.out.println("电梯开启...");
        super.context.setLiftState(Context.openingState);
    }

    @Override
    public void run() {
        System.out.println("电梯运行...");
        super.context.setLiftState(Context.runningState);
    }

    @Override
    public void stop() {
        System.out.println("电梯停止...");
        super.context.setLiftState(Context.stoppingState);
    }
}
