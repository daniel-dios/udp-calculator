package server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilsTest {

    @ParameterizedTest
    @CsvSource({"0,1,2", "3,1,2", "-3,3,4"})
    void shouldReturnEmptyWhenOutOfRange(String arg, int min, int max) {
        assertThat(NumberUtils.validateRange(arg, min, max)).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenIsNotANumber() {
        assertThat(NumberUtils.validateRange("a", 0, 2)).isEmpty();
    }


    @ParameterizedTest
    @CsvSource({"-1,-2,2,-1", "0,-2,2,0", "1,1,2,1", "2,1,2,2", "4,3,4,4"})
    void shouldReturnEmptyWhenOutOfRange(String arg, int min, int max, int expectedNumber) {
        assertThat(NumberUtils.validateRange(arg, min, max)).isPresent().get().isEqualTo(expectedNumber);
    }
}
