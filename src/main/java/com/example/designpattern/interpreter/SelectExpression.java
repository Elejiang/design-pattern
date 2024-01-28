package com.example.designpattern.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

class SelectExpression implements Expression{
    private List<Expression> cols = new ArrayList<>();

    public void addCols(Expression col) {
        cols.add(col);
    }

    @Override
    public String interpret(Context context) {
        StringJoiner interpretResult = new StringJoiner(", ");
        for (Expression col : cols) {
            interpretResult.add(col.interpret(context));
        }
        context.getResult().append("返回").append(interpretResult).append("列");
        return interpretResult.toString();
    }
}
