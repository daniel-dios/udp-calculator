package server.service;

public class CalculatorResult {
    private final int value;

    public CalculatorResult(final int value) {
        this.value = value;
    }

    public byte[] asBytes() {
        return String.valueOf(this.value).getBytes();
    }
}
