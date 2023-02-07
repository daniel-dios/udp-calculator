package client;

import org.junit.jupiter.api.Test;

import static client.OperationSymbol.DIV;
import static client.OperationSymbol.MUL;
import static client.OperationSymbol.SUBS;
import static client.OperationSymbol.SUM;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationSymbolTest {

    @Test
    void shouldReturnEmptyWhenOperationIsNotValid() {
        assertThat(OperationSymbol.parse("sum")).isEmpty();
    }

    @Test
    void shouldReturnOperation() {
        assertThat(OperationSymbol.parse("+"))
                .isNotEmpty()
                .get()
                .isEqualTo(SUM);
        assertThat(OperationSymbol.parse("-"))
                .isNotEmpty()
                .get()
                .isEqualTo(SUBS);
        assertThat(OperationSymbol.parse("x"))
                .isNotEmpty()
                .get()
                .isEqualTo(MUL);
        assertThat(OperationSymbol.parse("X"))
                .isNotEmpty()
                .get()
                .isEqualTo(MUL);
        assertThat(OperationSymbol.parse("*"))
                .isNotEmpty()
                .get()
                .isEqualTo(MUL);
        assertThat(OperationSymbol.parse(":"))
                .isNotEmpty()
                .get()
                .isEqualTo(DIV);
        assertThat(OperationSymbol.parse("/"))
                .isNotEmpty()
                .get()
                .isEqualTo(DIV);
    }
}
