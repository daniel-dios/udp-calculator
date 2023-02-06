package client;

import java.util.Optional;

public class Numb {
    private final int val;

    Numb(final int val) {
        this.val = val;
    }

    public static Optional<Numb> parse(final String arg) {
        System.out.println("Numb is invalid.");

        return Optional.empty();
    }
}
