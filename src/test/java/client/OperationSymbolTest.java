package client;

import org.junit.jupiter.api.Test;
import shared.OperationSymbol;

import static org.assertj.core.api.Assertions.assertThat;
import static shared.OperationSymbol.DIV;
import static shared.OperationSymbol.MUL;
import static shared.OperationSymbol.SUBS;
import static shared.OperationSymbol.SUM;

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
