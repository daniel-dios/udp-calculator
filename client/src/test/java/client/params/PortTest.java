package client.params;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PortTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(Port.parse("123asd")).isEmpty();
        assertThat(Port.parse("65536")).isEmpty();
        assertThat(Port.parse("-1")).isEmpty();
    }

    @Test
    void shouldReturnPort() {
        assertThat(Port.parse("8080"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new Port(8080));
    }
}
