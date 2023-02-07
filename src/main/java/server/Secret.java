package server;

import java.util.Optional;
import shared.NumberUtils;

public class Secret {
    private final int value;

    public Secret(final int value) {
        this.value = value;
    }

    public static Optional<Secret> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 255).map(Secret::new);
    }
}
