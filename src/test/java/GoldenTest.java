import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.Assertions.assertThat;

public class GoldenTest {

    @Test
    void shouldSpeakEachOtherInLocalHost() throws UnknownHostException {
        final var hostAddress = InetAddress.getLocalHost().getHostAddress();
        final var port = String.valueOf(new Random().nextInt(1000) + 8000);
        newFixedThreadPool(1).submit(() -> udpser.main(new String[]{port, "1"}));

        final String actual = udpcli.sync(new String[]{hostAddress, port, "3", "x", "2"});
        assertThat(actual).isEqualTo("7");
    }

    @Test
    void shouldUseServerManyTimes() throws UnknownHostException {
        final var hostAddress = InetAddress.getLocalHost().getHostAddress();
        final var port = String.valueOf(new Random().nextInt(1000) + 8000);
        newFixedThreadPool(1).submit(() -> udpser.main(new String[]{port, "11"}));

        final var strings = new HashMap<String[], String>();
        strings.put(new String[]{hostAddress, port, "3", "x", "2"}, "17");
        strings.put(new String[]{hostAddress, port, "1", "X", "2"}, "13");
        strings.put(new String[]{hostAddress, port, "2", "*", "2"}, "15");
        strings.put(new String[]{hostAddress, port, "255", "*", "255"}, "65036");
        strings.put(new String[]{hostAddress, port, "2", "+", "3"}, "16");
        strings.put(new String[]{hostAddress, port, "2", "-", "3"}, "10");
        strings.put(new String[]{hostAddress, port, "255", "-", "255"}, "11");
        strings.put(new String[]{hostAddress, port, "255", "/", "255"}, "12");
        strings.put(new String[]{hostAddress, port, "255", ":", "254"}, "12");
        strings.put(new String[]{hostAddress, port, "1", ":", "255"}, "11");

        strings.forEach((key, value) -> assertThat(udpcli.sync(key)).isEqualTo(value));
    }
}
