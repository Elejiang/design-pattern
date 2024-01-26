package com.example.designpattern.state;

class StoppingState extends LiftState {
    @Override
    public void open() {
        System.out.println("电梯开启...");
        super.context.setLiftState(Context.openingState);
    }

    @Override
    public void close() {
        System.out.println("电梯关闭...");
        super.context.setLiftState(Context.closingState);
    }

    @Override
    public void run() {
        System.out.println("电梯运行...");
        super.context.setLiftState(Context.runningState);
    }

    @Override
    public void stop() {
        System.out.println("电梯已处于停止状态...");
    }
}
