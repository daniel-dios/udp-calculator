import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.Assertions.assertThat;

public class GoldenTest {

    @Test
    void shouldSpeakEachOtherInLocalHost() throws UnknownHostException, ExecutionException, InterruptedException, TimeoutException {
        final var hostAddress = InetAddress.getLocalHost().getHostAddress();
        final var port = String.valueOf(new Random().nextInt(1000) + 8000);
        newFixedThreadPool(1).submit(() -> udpser.main(new String[]{port, "1"}));

        sleep(10000);

        final var client = newFixedThreadPool(1).submit(() -> udpcli.main(new String[]{hostAddress, port, "3", "x", "2"}));

        final String actual = client.get(20, TimeUnit.SECONDS);

        assertThat(actual).isEqualTo("7");
    }
}
