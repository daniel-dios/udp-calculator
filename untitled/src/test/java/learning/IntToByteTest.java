package learning;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntToByteTest {

    private static final int MAX_LEN = 65280;

    @Test
    void intToByteConversion() {
        assertThat(255 * 255 + 255).isEqualTo(MAX_LEN);

        final byte[] asStringInByte = String.valueOf(MAX_LEN).getBytes();
        final byte[] asBigIntInByte = BigInteger.valueOf(MAX_LEN).toByteArray();

        assertThat(asBigIntInByte.length).isLessThan(asStringInByte.length);

        assertThat(new BigInteger(asBigIntInByte)).isEqualTo(MAX_LEN);

        final BigInteger negative = BigInteger.valueOf(-1);
        assertThat(new BigInteger(negative.toByteArray()).toString()).isEqualTo("-1");
    }

    @Test
    void stupidTest() {
        for (int i = 0; i < 255 * 255 + 255; i++) {
            assertThat(BigInteger.valueOf(i).toByteArray().length)
                    .isLessThanOrEqualTo(BigInteger.valueOf(MAX_LEN).toByteArray().length);
        }
    }
}
