package client.params;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Optional;

public class Parameters {

    private final IP ip;
    private final Port port;
    private final Operation operation;

    Parameters(
            final IP ip,
            final Port port,
            final Operation operation
    ) {
        this.ip = ip;
        this.port = port;
        this.operation = operation;
    }

    public static Optional<Parameters> parse(final String[] args) {
        if (args.length != 3) {
            System.out.println("Arguments are not 3.");
            return Optional.empty();
        }

        final var ip = IP.parse(args[0]);
        final var port = Port.parse(args[1]);
        final var operation = Operation.parse(args[2]);

        return ip.isPresent() && port.isPresent() && operation.isPresent()
                ? Optional.of(new Parameters(ip.get(), port.get(), operation.get()))
                : Optional.empty();
    }

    public DatagramPacket toDatagramPacket() {
        return new DatagramPacket(
                this.operation.toServerRequest(),
                this.operation.toServerRequest().length,
                this.ip.getValue(),
                this.port.getValue()
        );
    }
}
