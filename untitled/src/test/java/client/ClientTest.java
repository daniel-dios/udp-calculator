package client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    void shouldSendMessages() throws ExecutionException, InterruptedException, TimeoutException, UnknownHostException {
        final int port = new Random().nextInt(1000) + 8000;
        final ClientParameters x = buildRequest(port, "0", "x", "255");
        Callable<String> task = () -> listen(port);

        Future<String> future = Executors.newFixedThreadPool(1)
                .submit(task);

        Executors.newFixedThreadPool(1)
                .submit(() -> Client.sendRequest(x));

        final String receivedDataInServer = future.get(10, TimeUnit.SECONDS);
        assertThat(receivedDataInServer)
                .contains("0x255");
    }

    @Test
    void shouldReceiveMessages() throws UnknownHostException {
        final int port = new Random().nextInt(1000) + 8000;
        final ClientParameters x = buildRequest(port, "0", "x", "255");

        Executors.newFixedThreadPool(1)
                .submit(() -> send(port, "65280"));

        final Optional<BigInteger> bigInteger = Client.sendRequest(x);

        assertThat(bigInteger).isPresent().get().isEqualTo(BigInteger.valueOf(65280));
    }

    private String send(final int port, final String s) {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Listening...");

            final byte[] receiveData = new byte[7];
            final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            System.out.println("[UDP " + receivePacket.getAddress() + "]");

            DatagramPacket sendPacket = new DatagramPacket(
                    s.getBytes(),
                    "65280".getBytes().length,
                    receivePacket.getAddress(),
                    receivePacket.getPort()
            );
            serverSocket.send(sendPacket);

            return new String(receivePacket.getData());
        } catch (IOException e) {
            System.out.println("IO ERROR");
            return "error";
        }
    }

    private static ClientParameters buildRequest(final int port, final String first, final String operation, final String second) throws UnknownHostException {
        final IP ip = new IP(InetAddress.getLocalHost());
        return new ClientParameters(
                ip,
                Port.parse(String.valueOf(port)).get(),
                Numb.parse(first).get(),
                Operation.parse(operation).get(),
                Numb.parse(second).get()
        );
    }

    private String listen2(final int port) throws InterruptedException {
        System.out.println("yoooo");
        sleep(10000);
        return "hi";
    }

    private String listen(final int port) {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Listening...");

            final byte[] receiveData = new byte[7];
            final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            System.out.println("[UDP " + receivePacket.getAddress() + "]");

            DatagramPacket sendPacket = new DatagramPacket(
                    BigInteger.valueOf(1).toByteArray(),
                    BigInteger.valueOf(1).toByteArray().length,
                    receivePacket.getAddress(),
                    receivePacket.getPort()
            );
            serverSocket.send(sendPacket);

            return new String(receivePacket.getData());
        } catch (IOException e) {
            System.out.println("IO ERROR");
            return "error";
        }
    }
}
