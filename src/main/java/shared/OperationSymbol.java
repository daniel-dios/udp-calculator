package shared;

import java.util.Optional;

public enum OperationSymbol {
    SUM("+", "\\+"),
    SUBS("-", "\\-"),
    MUL("x", "x"),
    DIV(":", "\\:");

    public final String symbol;
    public final String regex;

    OperationSymbol(final String symbol, final String regex) {
        this.symbol = symbol;
        this.regex = regex;
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
