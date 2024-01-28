package com.example.designpattern.interpreter;

class Client {
    private String[] SQL;

    private Context context = new Context();

    private int nextExpressionIndex = 0;

    public Client(String SQL) {
        this.SQL = SQL.split("[\\s,=;]+");
    }

    public String getInterpretResult() {
        String sqlType = nextExpression();
        switch (sqlType) {
            case "select" : return interpretSelectSQL();
            case "update" : return "暂不支持解析update语句";
            default: return "无法解析SQL";
        }
    }

    private String nextExpression() {
        return nextExpressionIndex < SQL.length ? SQL[nextExpressionIndex++] : null;
    }

    private String interpretSelectSQL() {
        SelectExpression selectExpression = new SelectExpression();
        String col = nextExpression();
        while (!"from".equals(col)) {
            selectExpression.addCols(new StringTerminalExpression(col));
            col = nextExpression();
        }

        FromExpression fromExpression = new FromExpression(new StringTerminalExpression(nextExpression()));

        nextExpression();
        WhereExpression whereExpression = new WhereExpression(new StringTerminalExpression(nextExpression()), new StringTerminalExpression(nextExpression()));

        fromExpression.interpret(context);
        whereExpression.interpret(context);;
        selectExpression.interpret(context);
        return context.getResult().toString();
    }
}
