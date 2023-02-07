package server.service;

import shared.OperableNumber;
import shared.OperationSymbol;
import shared.OperationResult;

public class CalculatorService {

    public static final CalculatorInputException CALCULATOR_INPUT_EXCEPTION = new CalculatorInputException("I can't divide by Zero");

    public OperationResult calculate(final OperationSymbol operationSymbol, final OperableNumber first, final OperableNumber second) throws CalculatorInputException {
        switch (operationSymbol) {
            case SUM:
                return first.add(second);
            case SUBS:
                return first.minus(second);
            case MUL:
                return first.mul(second);
            case DIV:
                if (second.isZero()) {
                    throw CALCULATOR_INPUT_EXCEPTION;
                }
                return first.div(second);
        }
        throw new RuntimeException("Unreachable code");
    }

    public static class CalculatorInputException extends Throwable {
        public CalculatorInputException(final String s) {
            super(s);
        }
    }
}
