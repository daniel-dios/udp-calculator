package client;

import java.util.Optional;

public class IP {
    private final String val;

    IP(final String val) {
        this.val = val;
    }

    public static Optional<IP> parse(final String arg) {
        System.out.println("IP is not valid.");
        return Optional.empty();
    }
}
