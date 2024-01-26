package com.example.designpattern.state;

class Main {
    public static void main(String[] args) {
        Context context = new Context();
        // 默认初始停止状态
        context.setLiftState(new ClosingState());
        context.stop();
        context.open();
        context.run();
        context.close();
        context.run();
        context.open();
        context.close();
        context.run();
        context.stop();
    }
}
