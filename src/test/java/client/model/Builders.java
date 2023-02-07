package client.model;

import client.model.ClientParameters;
import client.model.IP;
import client.model.OperableNumber;
import client.model.OperationSymbol;
import client.model.Port;
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

    public static OperableNumber number(int number) {
        return OperableNumber.parse(String.valueOf(number)).get();
    }

    public static OperableNumber number(final String s) {
        return OperableNumber.parse(s).get();
    }

    public static IP ip(final String s) {
        return IP.parse(s).get();
    }

    public static ClientParameters buildRequest(final int port, final String first, final String operation, final String second) throws UnknownHostException {
        return new ClientParameters(
                ip(InetAddress.getLocalHost().getHostAddress()),
                port(port),
                number(first),
                operation(operation),
                number(second)
        );
    }
}
