package server.service;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import server.Secret;
import shared.OperableNumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static shared.Operation.SUM;

public class CalculatorServiceTest {

    public static Stream<Arguments> getSumOperations() {
        return Stream.of(
                of(number("1"), number("2"), result(1 + 2)),
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
            final OperableNumber first,
            final OperableNumber second,
            final CalculatorResult expected
    ) {
        final var actual = new CalculatorService().calculate(SUM, first, second);

        assertThat(actual).isEqualTo(expected);
    }

    private static CalculatorResult result(final int value) {
        return new CalculatorResult(value);
    }

    private static OperableNumber number(final String arg) {
        return OperableNumber.parse(arg).get();
    }

    private static Secret secret(final String arg) {
        return Secret.parse(arg).get();
    }
}
