package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class IP {
    private final InetAddress val;

    IP(final InetAddress val) {
        this.val = val;
    }

    public static Optional<IP> parse(final String arg) {
        try {
            if (arg.split("\\.").length != 4) {
                System.out.println("IP is not valid format X.X.X.X.");
                return Optional.empty();
            }
            return Optional.of(new IP(InetAddress.getByName(arg)));
        } catch (UnknownHostException e) {
            System.out.println("IP is not valid.");
            return Optional.empty();
        }
    }

    public InetAddress getValue() {
        return this.val;
    }
}
