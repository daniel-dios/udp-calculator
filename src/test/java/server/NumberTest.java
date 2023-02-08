package server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(Number.parse("123asd")).isEmpty();
        assertThat(Number.parse("256")).isEmpty();
        assertThat(Number.parse("-1")).isEmpty();
    }

    @Test
    void shouldReturnNumb() {
        assertThat(Number.parse("123"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new Number(123));
    }
}
