package server.operation;

public class Operation {
    private final Symbol symbol;
    private final Number first;
    private final Number second;

    public Operation(
            final Symbol symbol,
            final Number first,
            final Number second
    ) {
        this.symbol = symbol;
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%s%s%s)", first, symbol, second);
    }

    public Symbol operation() {
        return this.symbol;
    }

    public Number first() {
        return this.first;
    }

    public Number second() {
        return this.second;
    }
}
