import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
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

        sleep(5000);

        final String actual = udpcli.sync(new String[]{hostAddress, port, "3", "x", "2"});
        assertThat(actual).isEqualTo("7");
    }
}
