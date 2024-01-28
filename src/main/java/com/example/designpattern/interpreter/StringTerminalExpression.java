package com.example.designpattern.interpreter;

class StringTerminalExpression implements Expression{
    private String val;

    public StringTerminalExpression(String val) {
        this.val = val;
    }
    @Override
    public String interpret(Context context) {
        String interpretResult = context.getValue(val);
        return interpretResult == null ? val : interpretResult;
    }
}
