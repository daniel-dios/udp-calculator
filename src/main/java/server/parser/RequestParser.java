package server.parser;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import shared.OperableNumber;
import shared.Operation;

public class RequestParser {

    public Optional<Request> parse(final byte[] receivedText) {
        final var trim = new String(receivedText).trim();
        final var op = Arrays.stream(Operation.values())
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

        final var first = OperableNumber.parse(numbers[0]);
        final var second = OperableNumber.parse(numbers[1]);
        if (first.isEmpty() || second.isEmpty()) {
            System.out.printf("Numbers %s are not valid.%n", Arrays.stream(numbers).collect(Collectors.toList()));
            return Optional.empty();
        }

        return Optional.of(new Request(op.get(), first.get(), second.get()));
    }
}
