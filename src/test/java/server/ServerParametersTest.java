package server;

import org.junit.jupiter.api.Test;
import utils.Builders;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Builders.secret;

public class ServerParametersTest {

    @Test
    void shouldReturnEmptyWhenErrors() {
        assertThat(ServerParameters.parse(new String[]{"a"})).isEmpty();
        assertThat(ServerParameters.parse(new String[]{"ip", "port", "wtf"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenPortIsNotValid() {
        assertThat(ServerParameters.parse(new String[]{"asdf", "4"})).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFirstNumberIsNotValid() {
        assertThat(ServerParameters.parse(new String[]{"9000", "4asd"})).isEmpty();
        assertThat(ServerParameters.parse(new String[]{"9000", "asd"})).isEmpty();
        assertThat(ServerParameters.parse(new String[]{"9000", "455"})).isEmpty();
    }

    @Test
    void shouldReturnValid() {
        final var port = "9000";
        final var secret = "4";
        final var input = new String[]{port, secret};

        final var parse = ServerParameters.parse(input);

        final var expected = new ServerParameters(
                Builders.port(port),
                secret(secret)
        );
        assertThat(parse)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
