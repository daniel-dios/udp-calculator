package client;

import java.util.Optional;
import contract.GlobalConstants;

public enum OperationSymbol {
    SUM(GlobalConstants.SUM),
    SUBS(GlobalConstants.SUBS),
    MUL(GlobalConstants.MUL),
    DIV(GlobalConstants.DIV);

    public final String symbol;

    OperationSymbol(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static Optional<OperationSymbol> parse(final String arg) {
        switch (arg) {
            case "+":
                return Optional.of(SUM);
            case "-":
                return Optional.of(SUBS);
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
