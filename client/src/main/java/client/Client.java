package client;

import client.params.Parameters;
import client.response.ServerResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.time.Duration;

import static client.response.ServerResponse.ko;
import static client.response.ServerResponse.ok;
import static client.response.ServerResponse.timeout;
import static client.contract.GlobalConstants.isKO;

public class Client {

    public static ServerResponse sendRequest(Parameters params, final Duration timeout) {
        try (final var ds = new DatagramSocket()) {
            ds.setSoTimeout((int) timeout.toMillis());

            ds.send(params.toDatagramPacket());

            final var received = new DatagramPacket(new byte[5], 5); // -255 to 65280 are 5 characters as max.
            ds.receive(received);

            final var trim = new String(received.getData()).trim();
            return isKO(trim) ? ko(trim) : ok(trim);
        } catch (SocketTimeoutException e) {
            return timeout(timeout);
        } catch (SecurityException e) {
            return ko("SM doesn't allow the send.");
        } catch (PortUnreachableException e) {
            return ko("Socket connected to unreachable destination.");
        } catch (IllegalBlockingModeException e) {
            return ko("Socket channel in non-blocking mode.");
        } catch (IllegalArgumentException e) {
            return ko("Socket address and packet address differ.");
        } catch (IOException e) {
            return ko("I/O");
        }
    }
}
