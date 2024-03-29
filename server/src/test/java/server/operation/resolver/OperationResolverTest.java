package server.operation.resolver;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import server.operation.Number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static server.Builders.number;
import static server.Builders.result;
import static server.operation.Symbol.DIV;
import static server.operation.Symbol.MUL;
import static server.operation.Symbol.SUB;
import static server.operation.Symbol.ADD;

public class OperationResolverTest {

    public static Stream<Arguments> getSumOperations() {
        return Stream.of(
                of(number("1"), number("2"), result(1 + 2)),
                of(number("255"), number("0"), result(255)),
                of(number("0"), number("23"), result(23)),
                of(number("2"), number("23"), result(2 + 23)),
                of(number("123"), number("255"), result(123 + 255)),
                of(number("255"), number("255"), result(255 + 255)),
                of(number("255"), number("2"), result(255 + 2)),
                of(number("2"), number("2"), result(2 + 2)),
                of(number("3"), number("0"), result(3)),
                of(number("1"), number("255"), result(1 + 255))
        );
    }

    @ParameterizedTest
    @MethodSource("getSumOperations")
    void shouldSum(
            final Number first,
            final Number second,
            final OperationResult expected
    ) throws OperationResolver.CalculatorInputException {
        final var actual = new OperationResolver().compute(ADD, first, second);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> getSubOperations() {
        return Stream.of(
                of(number("1"), number("2"), result(1 - 2)),
                of(number("255"), number("0"), result(255)),
                of(number("0"), number("23"), result(-23)),
                of(number("2"), number("23"), result(2 - 23)),
                of(number("123"), number("255"), result(123 - 255)),
                of(number("255"), number("255"), result(0)),
                of(number("255"), number("2"), result(255 - 2)),
                of(number("2"), number("2"), result(0)),
                of(number("3"), number("0"), result(3)),
                of(number("1"), number("255"), result(1 - 255))
        );
    }

    @ParameterizedTest
    @MethodSource("getSubOperations")
    void shouldSub(
            final Number first,
            final Number second,
            final OperationResult expected
    ) throws OperationResolver.CalculatorInputException {
        final var actual = new OperationResolver().compute(SUB, first, second);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> getMulOperations() {
        return Stream.of(
                of(number("1"), number("2"), result(2)),
                of(number("255"), number("0"), result(0)),
                of(number("0"), number("23"), result(0)),
                of(number("2"), number("23"), result(2 * 23)),
                of(number("123"), number("255"), result(123 * 255)),
                of(number("255"), number("255"), result(255 * 255)),
                of(number("255"), number("2"), result(255 * 2)),
                of(number("2"), number("2"), result(2 * 2)),
                of(number("3"), number("0"), result(0)),
                of(number("1"), number("255"), result(255))
        );
    }

    @ParameterizedTest
    @MethodSource("getMulOperations")
    void shouldMul(
            final Number first,
            final Number second,
            final OperationResult expected
    ) throws OperationResolver.CalculatorInputException {
        final var actual = new OperationResolver().compute(MUL, first, second);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> getDivOperations() {
        return Stream.of(
                of(number("1"), number("2"), result(0)),
                of(number("0"), number("23"), result(0)),
                of(number("2"), number("23"), result(0)),
                of(number("123"), number("255"), result(0)),
                of(number("255"), number("255"), result(1)),
                of(number("255"), number("2"), result(127)),
                of(number("2"), number("2"), result(1)),
                of(number("6"), number("2"), result(3)),
                of(number("255"), number("3"), result(85))
        );
    }

    @ParameterizedTest
    @MethodSource("getDivOperations")
    void shouldDiv(
            final Number first,
            final Number second,
            final OperationResult expected
    ) throws OperationResolver.CalculatorInputException {
        final var actual = new OperationResolver().compute(DIV, first, second);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionWhenDivByZero() {
        assertThatThrownBy(() -> new OperationResolver().compute(DIV, number("1"), number("0")))
                .isInstanceOf(OperationResolver.CalculatorInputException.class);
    }
}
