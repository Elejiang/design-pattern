package com.example.designpattern.interpreter;

class AddExpression implements Expression{
    private Expression left;
    private Expression right;
    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public int interpret(Context context) {
        return left.interpret(context) + right.interpret(context);
    }
}
