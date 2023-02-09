package server.operation;


import server.contract.GlobalConstants;

public enum Symbol {
    ADD(GlobalConstants.ADD, "\\" + GlobalConstants.ADD),
    SUB(GlobalConstants.SUB, "\\" + GlobalConstants.SUB),
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
