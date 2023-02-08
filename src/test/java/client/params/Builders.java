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

    public static OperationSymbol operation(final String operation) {
        return OperationSymbol.parse(operation).get();
    }

    public static InputNumber number(int number) {
        return InputNumber.parse(String.valueOf(number)).get();
    }

    public static InputNumber number(final String s) {
        return InputNumber.parse(s).get();
    }

    public static IP ip(final String s) {
        return IP.parse(s).get();
    }

    public static Parameters buildRequest(final int port, final String first, final String operation, final String second) throws UnknownHostException {
        return new Parameters(
                ip(InetAddress.getLocalHost().getHostAddress()),
                port(port),
                number(first),
                operation(operation),
                number(second)
        );
    }
}
