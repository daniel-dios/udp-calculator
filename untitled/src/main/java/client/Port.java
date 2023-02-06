package client;

import java.util.Optional;

public class Port {
    private final int val;

    Port(final int val) {
        this.val = val;
    }

    public static Optional<Port> parse(final String arg) {
        System.out.println("Port is not valid.");

        return Optional.empty();
    }
}
