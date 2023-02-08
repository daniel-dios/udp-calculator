package server.operation;

import contract.GlobalConstants;

public enum Symbol {
    SUM(GlobalConstants.SUM, "\\" + GlobalConstants.SUM),
    SUBS(GlobalConstants.SUBS, "\\" + GlobalConstants.SUBS),
    MUL(GlobalConstants.MUL, GlobalConstants.MUL),
    DIV(GlobalConstants.DIV, "\\" + GlobalConstants.DIV);

    public final String symbol;
    public final String regex;

    Symbol(final String symbol, final String regex) {
        this.symbol = symbol;
        this.regex = regex;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
