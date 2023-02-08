package server;

import server.operation.Number;
import server.secrets.Secret;
import server.operation.resolver.OperationResult;

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

    public static Number number(int number) {
        return Number.parse(String.valueOf(number)).get();
    }

    public static Number number(final String s) {
        return Number.parse(s).get();
    }

    public static Secret secret(final String secret) {
        return Secret.parse(secret).get();
    }
}
