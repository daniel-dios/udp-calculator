package server;

import java.util.Optional;
import server.secrets.Secret;
import shared.Port;

public class ServerParameters {
    private final Port port;
    private final Secret secret;

    ServerParameters(final Port port, final Secret secret) {
        this.port = port;
        this.secret = secret;
    }

    public static Optional<ServerParameters> parse(final String[] args) {
        if (args.length != 2) {
            System.out.println("Arguments are not 2");
            return Optional.empty();
        }
        final var port = Port.parse(args[0]);
        final var secret = Secret.parse(args[1]);

        return port.isPresent() && secret.isPresent()
                ? Optional.of(new ServerParameters(port.get(), secret.get()))
                : Optional.empty();
    }

    public Secret getSecret() {
        return this.secret;
    }

    public Port getPort() {
        return this.port;
    }
}
