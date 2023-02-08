package client.params;

import java.util.Optional;

class Port {
    private final int val;

    Port(final int val) {
        this.val = val;
    }

    static Optional<Port> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 65535).map(Port::new);
    }

    int getValue() {
        return this.val;
    }
}
