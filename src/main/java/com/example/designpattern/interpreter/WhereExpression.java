package com.example.designpattern.interpreter;

class WhereExpression implements Expression{
    private StringTerminalExpression filed;
    private StringTerminalExpression value;

    public WhereExpression(StringTerminalExpression filed, StringTerminalExpression value) {
        this.filed = filed;
        this.value = value;
    }

    @Override
    public String interpret(Context context) {
        String interpretResult = filed.interpret(context) + " = " + value.interpret(context);
        context.getResult().append(interpretResult).append("的数据，");
        return interpretResult;
    }
}
