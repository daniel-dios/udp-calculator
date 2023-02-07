package client;

import org.junit.jupiter.api.Test;

import static client.Operation.DIV;
import static client.Operation.MUL;
import static client.Operation.SUBS;
import static client.Operation.SUM;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    @Test
    void shouldReturnEmptyWhenOperationIsNotValid() {
        assertThat(Operation.parse("sum")).isEmpty();
    }

    @Test
    void shouldReturnOperation() {
        assertThat(Operation.parse("+"))
                .isNotEmpty()
                .get()
                .isEqualTo(SUM);
        assertThat(Operation.parse("-"))
                .isNotEmpty()
                .get()
                .isEqualTo(SUBS);
        assertThat(Operation.parse("x"))
                .isNotEmpty()
                .get()
                .isEqualTo(MUL);
        assertThat(Operation.parse("X"))
                .isNotEmpty()
                .get()
                .isEqualTo(MUL);
        assertThat(Operation.parse("*"))
                .isNotEmpty()
                .get()
                .isEqualTo(MUL);
        assertThat(Operation.parse(":"))
                .isNotEmpty()
                .get()
                .isEqualTo(DIV);
        assertThat(Operation.parse("/"))
                .isNotEmpty()
                .get()
                .isEqualTo(DIV);
    }

}
