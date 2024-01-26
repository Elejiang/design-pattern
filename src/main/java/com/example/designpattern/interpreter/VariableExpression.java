package com.example.designpattern.interpreter;

class VariableExpression implements Expression{
    private String variableName;
    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public int interpret(Context context) {
        return context.get(variableName);
    }
}
