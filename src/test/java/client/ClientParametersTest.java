package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;
import shared.OperableNumber;
import shared.Port;

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
    void shouldReturnValid() throws UnknownHostException {
        final var input = new String[]{"192.168.1.9", "9000", "4", "+", "5"};

        final var parse = ClientParameters.parse(input);

        final var expected = new ClientParameters(
                new IP(InetAddress.getByName("192.168.1.9")),
                Port.parse("9000").get(),
                buildNumb("4"),
                Operation.SUM,
                buildNumb("5")
        );
        assertThat(parse)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnEmptyWhenDivAndSecondNumbIsZero() {
        final var input = new String[]{"192.168.1.9", "9000", "4", "/", "0"};

        assertThat(ClientParameters.parse(input)).isEmpty();
    }

    private OperableNumber buildNumb(final String arg) {
        return OperableNumber.parse(arg).get();
    }
}
