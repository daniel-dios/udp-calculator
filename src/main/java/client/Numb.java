package client;

import java.util.Optional;
import shared.NumberUtils;

public class Numb {
    private final int val;

    Numb(final int val) {
        this.val = val;
    }

    public static Optional<Numb> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 255).map(Numb::new);
    }

    public boolean isZero() {
        return val == 0;
    }

    public int getVal() {
        return val;
    }
}
