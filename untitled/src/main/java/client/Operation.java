package client;

import java.util.Optional;

public enum Operation {
    SUM;

    public static Optional<Operation> parse(final String arg) {
        System.out.println("Operation is invalid.");

        return Optional.empty();
    }
}
