package client;

import java.net.InetAddress;
import java.util.Optional;
import shared.OperableNumber;
import shared.Port;

public class ClientParameters {

    private final IP ip;
    private final Port port;
    private final OperableNumber first;
    private final Operation operation;
    private final OperableNumber second;

    ClientParameters(final IP ip, final Port port, final OperableNumber first, final Operation operation, final OperableNumber second) {
        this.ip = ip;
        this.port = port;
        this.first = first;
        this.operation = operation;
        this.second = second;
    }

    public static Optional<ClientParameters> parse(final String[] args) {
        if (args.length != 5) {
            System.out.println("Arguments are not 5.");
            return Optional.empty();
        }

        final var ip = IP.parse(args[0]);
        final var port = Port.parse(args[1]);
        final var first = OperableNumber.parse(args[2]);
        final var op = Operation.parse(args[3]);
        final var second = OperableNumber.parse(args[4]);

        return ip.isPresent() && port.isPresent() && first.isPresent() && op.isPresent() && second.isPresent() && !isDivAndZero(op.get(), second.get())
                ? Optional.of(new ClientParameters(ip.get(), port.get(), first.get(), op.get(), second.get()))
                : Optional.empty();
    }

    private static boolean isDivAndZero(final Operation operation, final OperableNumber second) {
        if (operation.equals(Operation.DIV) && second.isZero()) {
            System.out.println("Second number must be not 0 when DIV");
            return true;
        }
        return false;
    }

    public byte[] toRequest() {
        return (first.getVal() + this.operation.getSymbol() + this.second.getVal()).getBytes();
    }

    public InetAddress address() {
        return this.ip.getValue();
    }

    public int port() {
        return this.port.getValue();
    }
}
