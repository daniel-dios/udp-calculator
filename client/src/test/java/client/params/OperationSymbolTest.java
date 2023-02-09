package client.params;

import org.junit.jupiter.api.Test;

import static client.params.OperationSymbol.DIV;
import static client.params.OperationSymbol.MUL;
import static client.params.OperationSymbol.SUB;
import static client.params.OperationSymbol.ADD;
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
                .isEqualTo(ADD);
        assertThat(OperationSymbol.parse("-"))
                .isNotEmpty()
                .get()
                .isEqualTo(SUB);
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
