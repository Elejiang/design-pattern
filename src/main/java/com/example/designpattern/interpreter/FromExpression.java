package com.example.designpattern.interpreter;

class FromExpression implements Expression {
    private Expression table;

    public FromExpression(Expression table) {
        this.table = table;
    }

    @Override
    public String interpret(Context context) {
        String interpretResult = "从" + table.interpret(context) + "表中查询";
        context.getResult().append(interpretResult);
        return interpretResult;
    }
}
