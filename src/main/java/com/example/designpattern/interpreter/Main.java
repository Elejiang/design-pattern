package com.example.designpattern.interpreter;

class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.set("x", 5);
        context.set("y", 7);
        Expression x = new VariableExpression("x");
        Expression y = new VariableExpression("y");
        Expression sum = new AddExpression(new AddExpression(x, y), new NumberExpression(1));

        System.out.println("x + y + 1 = " + sum.interpret(context));
    }
}
