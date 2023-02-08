package server.operation.resolver;

import server.operation.Number;
import server.operation.Symbol;

public class OperationResolver {

    public static final CalculatorInputException CALCULATOR_INPUT_EXCEPTION = new CalculatorInputException("I can't divide by Zero");

    public OperationResult compute(final Symbol symbol, final Number first, final Number second) throws CalculatorInputException {
        switch (symbol) {
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
