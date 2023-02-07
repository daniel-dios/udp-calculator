package server.parser;

import server.OperableNumber;
import server.OperationSymbol;

public class Request {
    private final OperationSymbol operationSymbol;
    private final OperableNumber first;
    private final OperableNumber second;

    public Request(
            final OperationSymbol operationSymbol,
            final OperableNumber first,
            final OperableNumber second
    ) {
        this.operationSymbol = operationSymbol;
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%s%s%s)", first, operationSymbol, second);
    }

    public OperationSymbol operation() {
        return this.operationSymbol;
    }

    public OperableNumber first() {
        return this.first;
    }

    public OperableNumber second() {
        return this.second;
    }
}
