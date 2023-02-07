package client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import shared.OperableNumber;
import shared.Operation;
import shared.Port;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    void shouldSendMessages() throws ExecutionException, InterruptedException, TimeoutException, UnknownHostException {
        final var port = new Random().nextInt(1000) + 8000;
        final var x = buildRequest(port, "0", "x", "255");
        final Callable<String> task = () -> listen(port);
        final var future = newFixedThreadPool(1)
                .submit(task);

        newFixedThreadPool(1)
                .submit(() -> Client.sendRequest(x));

        final var receivedDataInServer = future.get(10, TimeUnit.SECONDS);
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
        final var port = new Random().nextInt(1000) + 8000;
        final var x = buildRequest(port, "1", "+", "2");
        newFixedThreadPool(1)
                .submit(() -> serverAnswers(port, input));

        final var actual = Client.sendRequest(x);

        assertThat(actual).isPresent().get().isEqualTo(input);
    }

    private String serverAnswers(final int port, final String s) {
        try (final var serverSocket = new DatagramSocket(port)) {
            System.out.println("Listening...");

            final var receiveData = new byte[7];
            final var dp = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(dp);

            System.out.println("[UDP " + dp.getAddress() + "]");

            final var sendPacket = new DatagramPacket(
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
        return new ClientParameters(
                new IP(InetAddress.getLocalHost()),
                Port.parse(String.valueOf(port)).get(),
                OperableNumber.parse(first).get(),
                Operation.parse(operation).get(),
                OperableNumber.parse(second).get()
        );
    }

    private String listen(final int port) {
        try (final var serverSocket = new DatagramSocket(port)) {
            System.out.println("Listening...");

            final var receiveData = new byte[7];
            final var dp = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(dp);

            System.out.println("[UDP " + dp.getAddress() + "]");

            final var sendPacket = new DatagramPacket(
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
