package server.parser;

import shared.Operation;
import shared.OperableNumber;

public class Request {
    private final Operation operation;
    private final OperableNumber first;
    private final OperableNumber second;

    public Request(
            final Operation operation,
            final OperableNumber first,
            final OperableNumber second
    ) {
        this.operation = operation;
        this.first = first;
        this.second = second;
    }

    public Operation operation() {
        return this.operation;
    }

    public OperableNumber first() {
        return this.first;
    }

    public OperableNumber second() {
        return this.second;
    }
}
