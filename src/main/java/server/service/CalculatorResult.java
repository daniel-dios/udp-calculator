package server.service;

public class CalculatorResult {
    private final int value;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CalculatorResult that = (CalculatorResult) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public CalculatorResult(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
