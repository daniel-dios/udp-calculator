package client.params;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Builders {

    public static Port port(final String arg) {
        return Port.parse(arg).get();
    }

    public static Port port(final int port) {
        return Port.parse(String.valueOf(port)).get();
    }

    public static IP ip(final String s) {
        return IP.parse(s).get();
    }

    public static Operation operation(String s) {
        return Operation.parse(s).get();
    }

    public static Parameters buildRequest(final int port, final String operation) throws UnknownHostException {
        return new Parameters(
                ip(InetAddress.getLocalHost().getHostAddress()),
                port(port),
                operation(operation)
        );
    }
}
