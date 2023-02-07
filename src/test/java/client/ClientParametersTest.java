package client;

import org.junit.jupiter.api.Test;

import static client.Builders.ip;
import static client.Builders.number;
import static client.Builders.port;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientParametersTest {

    @Test
    void shouldReturnEmptyWhenErrors() {
        assertThat(ClientParameters.parse(new String[]{"a", "b"})).isEmpty();
        assertThat(ClientParameters.parse(new String[]{"ip", "port", "numb", "symb", "numb", "wtf"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenIpIsNotValid() {
        assertThat(ClientParameters.parse(new String[]{"19216819", "9000", "4", "+", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenPortIsNotValid() {
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "asdf", "4", "+", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFirstNumberIsNotValid() {
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "4asd", "+", "5"})).isEmpty();
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "asd", "+", "5"})).isEmpty();
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "455", "+", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenSymbolIsNotValid() {
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "4", "e", "5"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenSecondNumberIsNotValid() {
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "4", "+", "5a"})).isEmpty();
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "4", "+", "asd"})).isEmpty();
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "4", "+", "555"})).isEmpty();
    }

    @Test
    void shouldReturnValid() {
        final var port = "9000";
        final var first = "4";
        final var second = "5";
        final var input = new String[]{"192.168.1.9", port, first, "+", second};

        final var parse = ClientParameters.parse(input);

        final var expected = new ClientParameters(
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
        assertThat(ClientParameters.parse(new String[]{"192.168.1.9", "9000", "4", "/", "0"})).isEmpty();
    }
}
