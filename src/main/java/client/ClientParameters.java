package client;

import java.net.InetAddress;
import java.util.Optional;

public class ClientParameters {

    private final IP ip;
    private final Port port;
    private final OperableNumber first;
    private final OperationSymbol operationSymbol;
    private final OperableNumber second;

    ClientParameters(
            final IP ip,
            final Port port,
            final OperableNumber first,
            final OperationSymbol operationSymbol,
            final OperableNumber second
    ) {
        this.ip = ip;
        this.port = port;
        this.first = first;
        this.operationSymbol = operationSymbol;
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
        final var symbol = OperationSymbol.parse(args[3]);
        final var second = OperableNumber.parse(args[4]);

        return ip.isPresent() && port.isPresent() && first.isPresent() && symbol.isPresent() && second.isPresent() && !isDivAndZero(symbol.get(), second.get())
                ? Optional.of(new ClientParameters(ip.get(), port.get(), first.get(), symbol.get(), second.get()))
                : Optional.empty();
    }

    private static boolean isDivAndZero(final OperationSymbol operationSymbol, final OperableNumber second) {
        if (operationSymbol.equals(OperationSymbol.DIV) && second.isZero()) {
            System.out.println("Second number must be not 0 when DIV");
            return true;
        }
        return false;
    }

    public byte[] toRequest() {
        return (first.getVal() + this.operationSymbol.symbol + this.second.getVal()).getBytes();
    }

    public InetAddress address() {
        return this.ip.getValue();
    }

    public int port() {
        return this.port.getValue();
    }
}
