package com.example.designpattern.interpreter;

class NumberExpression implements Expression{
    private int num;
    public NumberExpression(int num) {
        this.num = num;
    }
    @Override
    public int interpret(Context context) {
        return num;
    }
}
