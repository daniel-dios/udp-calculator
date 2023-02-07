package shared;

public class OperationResult {
    private final int val;

    OperationResult(final int val) {
        this.val = val;
    }

    public static OperationResult parser(int val) {
        return new OperationResult(val);
    }

    @Override
    public String toString() {
        return "Result:" + val;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final OperationResult that = (OperationResult) o;

        return val == that.val;
    }

    @Override
    public int hashCode() {
        return val;
    }

    public int value() {
        return val;
    }
}
