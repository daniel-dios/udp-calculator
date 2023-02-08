package client.params;

import java.net.InetAddress;
import java.util.Optional;

public class Parameters {

    private final IP ip;
    private final Port port;
    private final InputNumber first;
    private final OperationSymbol operationSymbol;
    private final InputNumber second;

    Parameters(
            final IP ip,
            final Port port,
            final InputNumber first,
            final OperationSymbol operationSymbol,
            final InputNumber second
    ) {
        this.ip = ip;
        this.port = port;
        this.first = first;
        this.operationSymbol = operationSymbol;
        this.second = second;
    }

    public static Optional<Parameters> parse(final String[] args) {
        if (args.length != 5) {
            System.out.println("Arguments are not 5.");
            return Optional.empty();
        }

        final var ip = IP.parse(args[0]);
        final var port = Port.parse(args[1]);
        final var first = InputNumber.parse(args[2]);
        final var symbol = OperationSymbol.parse(args[3]);
        final var second = InputNumber.parse(args[4]);

        return ip.isPresent() && port.isPresent() && first.isPresent() && symbol.isPresent() && second.isPresent() && !isDivAndZero(symbol.get(), second.get())
                ? Optional.of(new Parameters(ip.get(), port.get(), first.get(), symbol.get(), second.get()))
                : Optional.empty();
    }

    private static boolean isDivAndZero(final OperationSymbol operationSymbol, final InputNumber second) {
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
