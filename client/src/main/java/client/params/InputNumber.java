package client.params;

import java.util.Optional;

public class InputNumber {
    private final int val;

    InputNumber(final int val) {
        this.val = val;
    }

    static Optional<InputNumber> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 255).map(InputNumber::new);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final InputNumber that = (InputNumber) o;

        return val == that.val;
    }

    @Override
    public int hashCode() {
        return val;
    }

    public boolean isZero() {
        return val == 0;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }
}
