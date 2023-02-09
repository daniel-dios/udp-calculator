package client.params;

import client.contract.GlobalConstants;
import java.util.Optional;

enum OperationSymbol {
    ADD(GlobalConstants.ADD, "\\" + GlobalConstants.ADD),
    SUB(GlobalConstants.SUB, "\\" + GlobalConstants.SUB),
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

    static Optional<OperationSymbol> parse(final String arg) {
        switch (arg) {
            case "+":
                return Optional.of(ADD);
            case "-":
                return Optional.of(SUB);
            case "x":
            case "X":
            case "*":
                return Optional.of(MUL);
            case ":":
            case "/":
                return Optional.of(DIV);
        }
        System.out.println("Operation is invalid.");
        return Optional.empty();
    }
}
