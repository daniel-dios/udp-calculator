package shared;

import java.util.Optional;
import server.service.CalculatorResult;

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

    public OperableNumber add(final OperableNumber second) {
        return new OperableNumber(this.val + second.val);
    }

    public CalculatorResult toCalculatorResult() {
        return new CalculatorResult(this.val);
    }
}
