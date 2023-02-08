package client.params;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InputNumberTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(InputNumber.parse("123asd")).isEmpty();
        assertThat(InputNumber.parse("256")).isEmpty();
        assertThat(InputNumber.parse("-1")).isEmpty();
    }

    @Test
    void shouldReturnNumb() {
        assertThat(InputNumber.parse("123"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new InputNumber(123));
    }
}
