package client.model;

import client.model.OperableNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OperableNumberTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(OperableNumber.parse("123asd")).isEmpty();
        assertThat(OperableNumber.parse("256")).isEmpty();
        assertThat(OperableNumber.parse("-1")).isEmpty();
    }

    @Test
    void shouldReturnNumb() {
        assertThat(OperableNumber.parse("123"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new OperableNumber(123));
    }
}
