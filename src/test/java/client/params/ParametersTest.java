package client.params;

import org.junit.jupiter.api.Test;

import static client.params.Builders.ip;
import static client.params.Builders.number;
import static client.params.Builders.port;
import static org.assertj.core.api.Assertions.assertThat;

public class ParametersTest {

    @Test
    void shouldReturnEmptyWhenErrors() {
        assertThat(Parameters.parse(new String[]{"a", "b"})).isEmpty();
        assertThat(Parameters.parse(new String[]{"ip", "port", "numb", "symb", "numb", "wtf"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenIpIsNotValid() {
        assertThat(Parameters.parse(new String[]{"19216819", "9000", "4", "+", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenPortIsNotValid() {
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "asdf", "4", "+", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFirstNumberIsNotValid() {
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "4asd", "+", "5"})).isEmpty();
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "asd", "+", "5"})).isEmpty();
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "455", "+", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenSymbolIsNotValid() {
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "4", "e", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenSecondNumberIsNotValid() {
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "4", "+", "5a"})).isEmpty();
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "4", "+", "asd"})).isEmpty();
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "4", "+", "555"})).isEmpty();
    }

    @Test
    void shouldReturnValid() {
        final var port = "9000";
        final var first = "4";
        final var second = "5";
        final var input = new String[]{"192.168.1.9", port, first, "+", second};

        final var parse = Parameters.parse(input);

        final var expected = new Parameters(
                ip("192.168.1.9"),
                port(port),
                number(first),
                OperationSymbol.SUM,
                number(second)
        );
        assertThat(parse)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnEmptyWhenDivAndSecondNumbIsZero() {
        assertThat(Parameters.parse(new String[]{"192.168.1.9", "9000", "4", "/", "0"})).isEmpty();
    }
}
