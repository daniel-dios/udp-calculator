package client.params;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static client.params.OperationSymbol.DIV;
import static client.params.OperationSymbol.MUL;
import static client.params.OperationSymbol.SUB;
import static client.params.OperationSymbol.ADD;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    public static Stream<Arguments> getValidOperations() {
        final var args = new ArrayList<Arguments>();

        for (int i = 0; i < 23; i++) {
            args.add(Arguments.of(i + "x4", new InputNumber(i), MUL, new InputNumber(4)));
            args.add(Arguments.of(i + ":4", new InputNumber(i), DIV, new InputNumber(4)));
            args.add(Arguments.of(i + "-4", new InputNumber(i), SUB, new InputNumber(4)));
            args.add(Arguments.of(i + "+4", new InputNumber(i), ADD, new InputNumber(4)));
        }

        for (int i = 245; i <= 255; i++) {
            args.add(Arguments.of(i + "x4", new InputNumber(i), MUL, new InputNumber(4)));
            args.add(Arguments.of(i + ":4", new InputNumber(i), DIV, new InputNumber(4)));
            args.add(Arguments.of(i + "-4", new InputNumber(i), SUB, new InputNumber(4)));
            args.add(Arguments.of(i + "+4", new InputNumber(i), ADD, new InputNumber(4)));
        }

        for (int i = 0; i < 23; i++) {
            args.add(Arguments.of("4x" + i, new InputNumber(4), MUL, new InputNumber(i)));
            if (i != 0) {
                args.add(Arguments.of("4:" + i, new InputNumber(4), DIV, new InputNumber(i)));
            }
            args.add(Arguments.of("4-" + i, new InputNumber(4), SUB, new InputNumber(i)));
            args.add(Arguments.of("4+" + i, new InputNumber(4), ADD, new InputNumber(i)));
        }

        for (int i = 245; i <= 255; i++) {
            args.add(Arguments.of("4x" + i, new InputNumber(4), MUL, new InputNumber(i)));
            args.add(Arguments.of("4:" + i, new InputNumber(4), DIV, new InputNumber(i)));
            args.add(Arguments.of("4-" + i, new InputNumber(4), SUB, new InputNumber(i)));
            args.add(Arguments.of("4+" + i, new InputNumber(4), ADD, new InputNumber(i)));
        }

        return args.stream();
    }

    @ParameterizedTest
    @MethodSource("getValidOperations")
    void shouldReturnOperations(final String arg, final InputNumber first, final OperationSymbol symbol, final InputNumber second) {
        assertThat(Operation.parse(arg))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new Operation(first, symbol, second));
    }

    @Test
    void shouldReturnEmptyForDivZero() {
        assertThat(Operation.parse("1:0"))
                .isEmpty();
    }
}
