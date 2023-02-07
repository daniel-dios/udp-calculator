package server;

import java.util.Optional;

public class Secret {
    private final int value;

    public Secret(final int value) {
        this.value = value;
    }

    public static Optional<Secret> parse(final String arg) {
        try {
            final int number = Integer.parseInt(arg);
            if (number < 0 || number > 255) {
                System.out.println("Numb is out of [0, 255] range.");
                return Optional.empty();
            }
            return Optional.of(new Secret(number));
        } catch (NumberFormatException e) {
            System.out.println("Numb is invalid.");
            return Optional.empty();
        }
    }
}
