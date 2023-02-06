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
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

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

    public static Stream<Arguments> calculateMinAndMax() {
        return Stream.of(
                Arguments.of(String.valueOf(255 * 255 + 255)),
                Arguments.of(String.valueOf(255 * 255 + 254)),
                Arguments.of(String.valueOf(1)),
                Arguments.of(String.valueOf(0)),
                Arguments.of(String.valueOf(-255))
        );
    }

    @ParameterizedTest
    @MethodSource("calculateMinAndMax")
    void shouldReceiveMessages(String input) throws UnknownHostException {
        final int port = new Random().nextInt(1000) + 8000;
        final ClientParameters x = buildRequest(port, "0", "x", "255");

        Executors.newFixedThreadPool(1)
                .submit(() -> send(port, input));

        final Optional<String> actual = Client.sendRequest(x);

        assertThat(actual).isPresent().get().isEqualTo(input);
    }

    private String send(final int port, final String s) {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Listening...");

            final byte[] receiveData = new byte[7];
            final DatagramPacket dp = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(dp);

            System.out.println("[UDP " + dp.getAddress() + "]");

            DatagramPacket sendPacket = new DatagramPacket(
                    s.getBytes(),
                    s.getBytes().length,
                    dp.getAddress(),
                    dp.getPort()
            );
            serverSocket.send(sendPacket);

            return new String(dp.getData());
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
            final DatagramPacket dp = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(dp);

            System.out.println("[UDP " + dp.getAddress() + "]");

            DatagramPacket sendPacket = new DatagramPacket(
                    BigInteger.valueOf(1).toByteArray(),
                    BigInteger.valueOf(1).toByteArray().length,
                    dp.getAddress(),
                    dp.getPort()
            );
            serverSocket.send(sendPacket);

            return new String(dp.getData());
        } catch (IOException e) {
            System.out.println("IO ERROR");
            return "error";
        }
    }
}
