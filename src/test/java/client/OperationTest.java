package client;

import org.junit.jupiter.api.Test;
import shared.Operation;

import static org.assertj.core.api.Assertions.assertThat;
import static shared.Operation.DIV;
import static shared.Operation.MUL;
import static shared.Operation.SUBS;
import static shared.Operation.SUM;

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
