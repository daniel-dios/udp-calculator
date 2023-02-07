package utils;

import client.IP;
import server.secrets.Secret;
import shared.OperableNumber;
import shared.OperationSymbol;
import shared.OperationResult;
import shared.Port;

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

    public static OperationSymbol operation(final String operation) {
        return OperationSymbol.parse(operation).get();
    }

    public static Secret secret(final String secret) {
        return Secret.parse(secret).get();
    }

    public static IP ip(final String s) {
        return IP.parse(s).get();
    }
}
