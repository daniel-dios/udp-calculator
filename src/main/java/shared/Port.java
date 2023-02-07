package shared;

import java.util.Optional;

public class Port {
    private final int val;

    Port(final int val) {
        this.val = val;
    }

    public static Optional<Port> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 65535).map(Port::new);
    }

    public int getValue() {
        return this.val;
    }
}
