package server;

import server.OperableNumber;
import server.Port;
import server.secrets.Secret;
import server.service.OperationResult;

public class Builders {

    public static OperationResult result(int val) {
        return OperationResult.parser(val);
    }

    public static Port port(final String arg) {
        return Port.parse(arg).get();
    }

    public static Port port(final int port) {
        return Port.parse(String.valueOf(port)).get();
    }

    public static OperableNumber number(int number) {
        return OperableNumber.parse(String.valueOf(number)).get();
    }

    public static OperableNumber number(final String s) {
        return OperableNumber.parse(s).get();
    }

    public static Secret secret(final String secret) {
        return Secret.parse(secret).get();
    }
}
