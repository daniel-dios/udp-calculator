package client;

import java.util.Optional;
import org.junit.jupiter.api.Test;

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
        String[] input = new String[]{"192.168.1.9", "9000", "4", "+", "5"};

        final Optional<ClientParameters> parse = ClientParameters.parse(input);

        final ClientParameters expected = new ClientParameters(
                new IP("192.168.1.9"),
                new Port(9000),
                new Numb(4),
                Operation.SUM,
                new Numb(5)
        );
        assertThat(parse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
