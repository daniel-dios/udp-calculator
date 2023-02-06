package client;

import java.util.Optional;

public class ClientParameters {

    private final IP ip;
    private final Port port;
    private final Numb firstNumb;
    private final Operation operation;
    private final Numb secondNumb;

    ClientParameters(
            final IP ip,
            final Port port,
            final Numb firstNumb,
            final Operation operation,
            final Numb secondNumb
    ) {

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

        final Optional<IP> ip = IP.parse(args[0]);
        final Optional<Port> port = Port.parse(args[1]);
        final Optional<Numb> firstNumb = Numb.parse(args[2]);
        final Optional<Operation> operation = Operation.parse(args[3]);
        final Optional<Numb> numb = Numb.parse(args[4]);

        if (ip.isPresent() && port.isPresent() && firstNumb.isPresent() && operation.isPresent() && numb.isPresent()) {
            return Optional.of(new ClientParameters(ip.get(), port.get(), firstNumb.get(), operation.get(), numb.get()));
        }

        return Optional.empty();
    }
}
