package server.service;

import shared.OperableNumber;
import shared.Operation;
import shared.OperationResult;

public class CalculatorService {
    public OperationResult calculate(final Operation operation, final OperableNumber first, final OperableNumber second) {
        switch (operation) {
            case SUM:
                return first.add(second);
            case SUBS:
                return first.minus(second);
            case MUL:
                return first.mul(second);
            case DIV:
                break;
        }
        return null;
    }
}
