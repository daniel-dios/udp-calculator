package client.params;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static client.params.OperationSymbol.DIV;

public class Operation {

    final OperationSymbol symbol;
    final InputNumber first;
    final InputNumber second;

    Operation(final InputNumber first, final OperationSymbol symbol, final InputNumber second) {
        this.symbol = symbol;
        this.first = first;
        this.second = second;
    }

    public static Optional<Operation> parse(String arg) {
        final var op = Arrays.stream(OperationSymbol.values())
                .filter(it -> arg.contains(it.symbol))
                .findFirst();

        if (op.isEmpty()) {
            System.out.println("Operation symbol not found.");
            return Optional.empty();
        }

        final var numbers = arg.split(op.get().regex);
        if (numbers.length != 2) {
            System.out.println("There are not two numbers.");
            return Optional.empty();
        }

        final var first = InputNumber.parse(numbers[0]);
        final var second = InputNumber.parse(numbers[1]);
        if (first.isEmpty() || second.isEmpty()) {
            System.out.printf("Numbers %s are not valid.%n", Arrays.stream(numbers).collect(Collectors.toList()));
            return Optional.empty();
        }
        if (second.get().isZero() && op.get().equals(DIV)) {
            System.out.println("I can't dive by 0.");
            return Optional.empty();
        }
        return Optional.of(new Operation(first.get(), op.get(), second.get()));
    }

    public byte[] toServerRequest() {
        return (String.format("%s%s%s", first.toString(), symbol.symbol, second.toString())).getBytes();
    }
}
