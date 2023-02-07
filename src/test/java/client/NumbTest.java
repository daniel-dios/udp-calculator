package client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumbTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(Numb.parse("123asd")).isEmpty();
        assertThat(Numb.parse("256")).isEmpty();
        assertThat(Numb.parse("-1")).isEmpty();
    }

    @Test
    void shouldReturnNumb() {
        assertThat(Numb.parse("123"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new Numb(123));
    }
}
