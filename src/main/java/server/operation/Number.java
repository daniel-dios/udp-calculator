package server.operation;

import java.util.Optional;
import server.NumberUtils;
import server.service.OperationResult;

public class Number {
    private final int val;

    Number(final int val) {
        this.val = val;
    }

    public static Optional<Number> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 255).map(Number::new);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Number that = (Number) o;

        return val == that.val;
    }

    @Override
    public int hashCode() {
        return val;
    }

    public boolean isZero() {
        return val == 0;
    }

    public OperationResult add(final Number second) {
        return new OperationResult(this.val + second.val);
    }

    public OperationResult minus(final Number second) {
        return new OperationResult(this.val - second.val);
    }

    public OperationResult mul(final Number second) {
        return new OperationResult(this.val * second.val);
    }

    public OperationResult div(final Number second) {
        return new OperationResult(this.val / second.val);
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }
}
