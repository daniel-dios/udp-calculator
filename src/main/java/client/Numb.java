package client;

import java.util.Optional;

public class Numb {
    private final int val;

    Numb(final int val) {
        this.val = val;
    }

    public static Optional<Numb> parse(final String arg) {
        try {
            final int number = Integer.parseInt(arg);
            if (number < 0 || number > 255) {
                System.out.println("Numb is out of [0, 255] range.");
                return Optional.empty();
            }
            return Optional.of(new Numb(number));
        } catch (NumberFormatException e) {
            System.out.println("Numb is invalid.");
            return Optional.empty();
        }
    }

    public boolean isZero() {
        return val == 0;
    }

    public int getVal() {
        return val;
    }
}
