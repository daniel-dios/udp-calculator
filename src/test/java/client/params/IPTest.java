package client.params;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IPTest {

    @Test
    void shouldReturnEmptyWhenInvalid() {
        assertThat(IP.parse("123")).isEmpty();
    }

    @Test
    void shouldReturnIP() throws UnknownHostException {
        assertThat(IP.parse("192.168.1.1"))
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(new IP(InetAddress.getByName("192.168.1.1")));
    }
}
