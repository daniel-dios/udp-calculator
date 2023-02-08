package client.params;

import client.contract.GlobalConstants;

enum OperationSymbol {
    SUM(GlobalConstants.SUM, "\\" + GlobalConstants.SUM),
    SUBS(GlobalConstants.SUBS, "\\" + GlobalConstants.SUBS),
    MUL(GlobalConstants.MUL, GlobalConstants.MUL),
    DIV(GlobalConstants.DIV, "\\" + GlobalConstants.DIV);

    final String symbol;
    final String regex;

    OperationSymbol(final String symbol, final String regex) {
        this.symbol = symbol;
        this.regex = regex;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
