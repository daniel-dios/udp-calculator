package server.operation;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static server.operation.Symbol.DIV;

public class OperationParser {

    public Optional<Operation> parse(final byte[] receivedText) {
        final var trim = new String(receivedText).trim();
        final var op = Arrays.stream(Symbol.values())
                .filter(it -> trim.contains(it.symbol))
                .findFirst();

        if (op.isEmpty()) {
            System.out.println("Operation symbol not found.");
            return Optional.empty();
        }

        final var numbers = trim.split(op.get().regex);
        if (numbers.length != 2) {
            System.out.println("There are not two numbers.");
            return Optional.empty();
        }

        final var first = Number.parse(numbers[0]);
        final var second = Number.parse(numbers[1]);
        if (first.isEmpty() || second.isEmpty()) {
            System.out.printf("Numbers %s are not valid.%n", Arrays.stream(numbers).collect(Collectors.toList()));
            return Optional.empty();
        }

        if (second.get().isZero() && op.get().equals(DIV)) {
            System.out.println("I can't dive by 0.");
            return Optional.empty();
        }

        return Optional.of(new Operation(op.get(), first.get(), second.get()));
    }
}
