package shared;

import java.util.Optional;

public class Port {
    private final int val;

    Port(final int val) {
        this.val = val;
    }

    public static Optional<Port> parse(final String arg) {
        try {
            final int port = Integer.parseInt(arg);
            if (port < 0 || port > 65535) {
                System.out.println("Port is out of [0, 65535] range.");
                return Optional.empty();
            }
            return Optional.of(new Port(port));
        } catch (NumberFormatException e) {
            System.out.println("Port is invalid.");
            return Optional.empty();
        }
    }

    public int getValue() {
        return this.val;
    }
}
