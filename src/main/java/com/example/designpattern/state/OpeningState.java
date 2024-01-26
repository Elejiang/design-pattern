package com.example.designpattern.state;

class OpeningState extends LiftState {
    @Override
    public void open() {
        System.out.println("电梯已处于开启状态...");
    }

    @Override
    public void close() {
        System.out.println("电梯关闭...");
        context.setLiftState(Context.closingState);
    }

    @Override
    public void run() {
        System.out.println("电梯处于开启状态，无法运行");
    }

    @Override
    public void stop() {
        System.out.println("电梯处于开启状态，不需要停止");
    }
}
