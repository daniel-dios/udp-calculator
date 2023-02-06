package client;

import java.util.Optional;

public enum Operation {
    SUM, SUBS, MUL, DIV;

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
