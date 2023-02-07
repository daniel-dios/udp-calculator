package server.service;

import shared.OperableNumber;
import shared.Operation;

public class CalculatorService {
    public CalculatorResult calculate(final Operation operation, final OperableNumber first, final OperableNumber second) {
        switch (operation) {
            case SUM:
                return first.add(second).toCalculatorResult();
        }
        return null;
    }
}
