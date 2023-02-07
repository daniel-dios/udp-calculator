package client;

import java.net.InetAddress;
import java.util.Optional;
import shared.Port;

public class ClientParameters {

    private final IP ip;
    private final Port port;
    private final Numb firstNumb;
    private final Operation operation;
    private final Numb secondNumb;

    ClientParameters(final IP ip, final Port port, final Numb firstNumb, final Operation operation, final Numb secondNumb) {
        this.ip = ip;
        this.port = port;
        this.firstNumb = firstNumb;
        this.operation = operation;
        this.secondNumb = secondNumb;
    }

    public static Optional<ClientParameters> parse(final String[] args) {
        if (args.length != 5) {
            System.out.println("Arguments are not 5.");
            return Optional.empty();
        }

        final var ip = IP.parse(args[0]);
        final var port = Port.parse(args[1]);
        final var first = Numb.parse(args[2]);
        final var op = Operation.parse(args[3]);
        final var second = Numb.parse(args[4]);

        return ip.isPresent() && port.isPresent() && first.isPresent() && op.isPresent() && second.isPresent() && !isDivAndZero(op.get(), second.get())
                ? Optional.of(new ClientParameters(ip.get(), port.get(), first.get(), op.get(), second.get()))
                : Optional.empty();
    }

    private static boolean isDivAndZero(final Operation operation, final Numb numb) {
        if (operation.equals(Operation.DIV) && numb.isZero()) {
            System.out.println("Second number must be not 0 when DIV");
            return true;
        }
        return false;
    }

    public byte[] toRequest() {
        return (firstNumb.getVal() + this.operation.getSymbol() + this.secondNumb.getVal()).getBytes();
    }

    public InetAddress address() {
        return this.ip.getValue();
    }

    public int port() {
        return this.port.getValue();
    }
}
