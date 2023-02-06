package client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Optional;

public class Client {

    public static Optional<BigInteger> sendRequest(ClientParameters params) {
        try (DatagramSocket ds = new DatagramSocket()) {
            ds.setSoTimeout(10000);
            final byte[] send = params.toRequest();
            final DatagramPacket datagramPacket = new DatagramPacket(send, send.length, params.address(), params.port());

            ds.send(datagramPacket);

            final byte[] receiveData = new byte[BigInteger.valueOf(65280).toByteArray().length];
            final DatagramPacket received = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(received);
            return Optional.of(new BigInteger(received.getData()));
        } catch (SecurityException e) {
            System.out.println(" – if a security manager exists and its checkMulticast or checkConnect method doesn't allow the send.");
            return Optional.empty();
        } catch (PortUnreachableException e) {
            System.out.println(" – may be thrown if the socket is connected to a currently unreachable destination. Note, there is no guarantee that the exception will be thrown.");
            return Optional.empty();
        } catch (IllegalBlockingModeException e) {
            System.out.println(" – if this socket has an associated channel, and the channel is in non-blocking mode.");
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            System.out.println(" – if the socket is connected, and connected address and packet address differ.");
            return Optional.empty();
        } catch (SocketTimeoutException e) {
            System.out.println(" – if setSoTimeout was previously called and the timeout has expired.");
            return Optional.empty();
        } catch (IOException e) {
            System.out.println(" – if an I/O error occurs.");
            return Optional.empty();
        }
    }
}
