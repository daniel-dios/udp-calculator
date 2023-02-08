package client.params;

import org.junit.jupiter.api.Test;

import static client.params.OperationSymbol.DIV;
import static client.params.OperationSymbol.MUL;
import static client.params.OperationSymbol.SUBS;
import static client.params.OperationSymbol.SUM;
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
