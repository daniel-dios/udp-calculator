package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Optional;

public class Client {

    public static Optional<String> sendRequest(ClientParameters params) {
        try (final var ds = new DatagramSocket()) {
            ds.setSoTimeout(10000);
            final var send = params.toRequest();
            final var datagramPacket = new DatagramPacket(send, send.length, params.address(), params.port());

            ds.send(datagramPacket);

            final var received = new DatagramPacket(new byte[5], 5); // -255 to 65280 are 5 characters as max.
            ds.receive(received);

            final byte[] data = received.getData();
            final String trim = new String(data).trim();
            System.out.printf("Received %d bytes from server %s %n", data.length, trim);
            return Optional.of(trim);
        } catch (SecurityException e) {
            System.out.println("A security manager exists and its checkMulticast or checkConnect method doesn't allow the send.");
            return Optional.empty();
        } catch (PortUnreachableException e) {
            System.out.println("The socket is connected to a currently unreachable destination. Note, there is no guarantee that the exception will be thrown.");
            return Optional.empty();
        } catch (IllegalBlockingModeException e) {
            System.out.println("This socket has an associated channel, and the channel is in non-blocking mode.");
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            System.out.println("The socket is connected, and connected address and packet address differ.");
            return Optional.empty();
        } catch (SocketTimeoutException e) {
            System.out.println("SetSoTimeout was previously called and the timeout has expired.");
            return Optional.empty();
        } catch (IOException e) {
            System.out.println("I/O error occurred.");
            return Optional.empty();
        }
    }
}
