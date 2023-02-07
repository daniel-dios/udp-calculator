package shared;

import java.util.Optional;

public class OperableNumber {
    private final int val;

    OperableNumber(final int val) {
        this.val = val;
    }

    public static Optional<OperableNumber> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 255).map(OperableNumber::new);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final OperableNumber that = (OperableNumber) o;

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

    public OperationResult add(final OperableNumber second) {
        return new OperationResult(this.val + second.val);
    }

    public OperationResult minus(final OperableNumber second) {
        return new OperationResult(this.val - second.val);
    }

    public OperationResult mul(final OperableNumber second) {
        return new OperationResult(this.val * second.val);
    }

    public OperationResult div(final OperableNumber second) {
        return new OperationResult(this.val / second.val);
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }
}
