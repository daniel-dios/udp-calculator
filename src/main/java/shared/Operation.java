package shared;

import java.util.Optional;

public enum Operation {
    SUM("+", "\\+"), SUBS("-", "\\-"), MUL("x", "x"), DIV(":", "\\:");

    public final String symbol;
    public final String regex;

    Operation(final String symbol, final String regex) {
        this.symbol = symbol;
        this.regex = regex;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static Optional<Operation> parse(final String arg) {
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
