package server.parser;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import server.OperationSymbol;

import static org.assertj.core.api.Assertions.assertThat;
import static server.Builders.number;

public class RequestParserTest {

    public static Stream<Arguments> getValidRequests() {
        final var list = new ArrayList<Arguments>();
        Arrays.stream(OperationSymbol.values()).forEach(operation -> {
            for (int first = 0; first <= 10; first++) {
                for (int second = 0; second <= 10; second++) {
                    if (operation.equals(OperationSymbol.DIV) && second == 0) {
                        continue;
                    }
                    list.add(Arguments.of(
                            String.format("%d%s%d", first, operation.symbol, second),
                            new Request(operation, number(first), number(second))
                    ));
                }
            }
        });

        Arrays.stream(OperationSymbol.values()).forEach(operation -> {
            for (int first = 245; first <= 255; first++) {
                for (int second = 245; second <= 255; second++) {
                    final var arguments = Arguments.of(String.format("%d%s%d", first, operation.symbol, second), new Request(operation, number(first), number(second)));
                    list.add(arguments);
                }
            }
        });
        return list.stream();


    }

    @ParameterizedTest
    @MethodSource("getValidRequests")
    void shouldReturnValidRequest(String incomingText, Request expectedRequests) {
        final var incoming = ByteBuffer.allocate(7).put(incomingText.getBytes()).array();

        final var actual = new RequestParser().parse(incoming);

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedRequests);
    }

    public static Stream<Arguments> getOutOfRangeRequests() {
        final var list = new ArrayList<Arguments>();
        Arrays.stream(OperationSymbol.values()).forEach(operation -> {
            for (int first = -10; first < 0; first++) {
                for (int second = -10; second < 0; second++) {
                    final var arguments = Arguments.of(String.format("%d%s%d", first, operation.symbol, second));
                    list.add(arguments);
                }
            }
        });

        Arrays.stream(OperationSymbol.values()).forEach(operation -> {
            for (int first = 256; first <= 260; first++) {
                for (int second = 256; second <= 260; second++) {
                    final var arguments = Arguments.of(String.format("%d%s%d", first, operation.symbol, second));
                    list.add(arguments);
                }
            }
        });
        return list.stream();
    }

    @ParameterizedTest
    @MethodSource("getOutOfRangeRequests")
    void shouldReturnEmpty(String incomingText) {
        final var incoming = ByteBuffer.allocate(7).put(incomingText.getBytes()).array();

        final var actual = new RequestParser().parse(incoming);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenDivZero() {
        final var incoming = ByteBuffer.allocate(7).put("1/0".getBytes()).array();

        final var actual = new RequestParser().parse(incoming);

        assertThat(actual).isEmpty();
    }
}
