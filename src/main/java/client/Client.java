package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.time.Duration;

import static client.Result.ko;
import static client.Result.ok;
import static client.Result.timeout;
import static shared.Constants.isKO;

public class Client {

    public static Result sendRequest(ClientParameters params, final Duration timeout) {
        try (final var ds = new DatagramSocket()) {
            ds.setSoTimeout((int) timeout.toMillis());
            final var send = params.toRequest();
            final var datagramPacket = new DatagramPacket(send, send.length, params.address(), params.port());

            ds.send(datagramPacket);
            final var received = new DatagramPacket(new byte[5], 5); // -255 to 65280 are 5 characters as max.
            ds.receive(received);

            final byte[] data = received.getData();
            final String trim = new String(data).trim();
            System.out.printf("Received %d bytes from server %s %n", data.length, trim);
            return isKO(trim) ? ko(trim) : ok(trim);
        } catch (SecurityException e) {
            System.out.println("A security manager exists and its checkMulticast or checkConnect method doesn't allow the send.");
            return ko("SM doesn't allow the send.");
        } catch (PortUnreachableException e) {
            System.out.println("The socket is connected to a currently unreachable destination. Note, there is no guarantee that the exception will be thrown.");
            return ko("Socket connected to unreachable destination.");
        } catch (IllegalBlockingModeException e) {
            System.out.println("This socket has an associated channel, and the channel is in non-blocking mode.");
            return ko("Socket channel in non-blocking mode.");
        } catch (IllegalArgumentException e) {
            System.out.println("The socket is connected, and connected address and packet address differ.");
            return ko("Socket address and packet address differ.");
        } catch (SocketTimeoutException e) {
            System.out.println("SetSoTimeout was previously called and the timeout has expired.");
            return timeout(timeout);
        } catch (IOException e) {
            System.out.println("I/O error occurred.");
            return ko("I/O");
        }
    }
}
