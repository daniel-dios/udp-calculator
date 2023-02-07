package server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SecretTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(Secret.parse("123asd")).isEmpty();
        assertThat(Secret.parse("256")).isEmpty();
        assertThat(Secret.parse("-1")).isEmpty();
    }

    @Test
    void shouldReturnNumb() {
        assertThat(Secret.parse("123"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new Secret(123));
    }
}
