package client;

import client.response.ResultStatus;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static client.model.Builders.buildRequest;
import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    void shouldSendMessages() throws ExecutionException, InterruptedException, TimeoutException, UnknownHostException {
        final var port = new Random().nextInt(1000) + 8000;
        final var x = buildRequest(port, "0", "x", "255");
        final Callable<String> task = () -> serverAnswers(port, "OK");
        final var future = newFixedThreadPool(1)
                .submit(task);

        newFixedThreadPool(1).submit(() -> {
            sleep(5000);
            return Client.sendRequest(x, Duration.ofSeconds(10));
        });

        final var receivedDataInServer = future.get(10, SECONDS);
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
    void shouldReceiveMessages(String input) throws UnknownHostException, ExecutionException, InterruptedException, TimeoutException {
        final var port = new Random().nextInt(1000) + 8000;
        final var x = buildRequest(port, "1", "+", "2");
        newFixedThreadPool(1)
                .submit(() -> serverAnswers(port, input));

        final var result = newFixedThreadPool(1).submit(() -> {
            sleep(5000);
            return Client.sendRequest(x, Duration.ofSeconds(10));
        });

        final var actual = result.get(100, SECONDS);
        assertThat(actual.status).isEqualTo(ResultStatus.OK);
        assertThat(actual.answer()).isEqualTo(input);
    }

    @Test
    void shouldHandleKO() throws UnknownHostException, InterruptedException, ExecutionException, TimeoutException {
        final var port = new Random().nextInt(1000) + 8000;
        final var x = buildRequest(port, "1", "+", "2");
        newFixedThreadPool(1)
                .submit(() -> serverAnswers(port, "KO"));

        final var result = newFixedThreadPool(1).submit(() -> {
            sleep(5000);
            return Client.sendRequest(x, Duration.ofSeconds(10));
        });

        final var actual = result.get(10, SECONDS);
        assertThat(actual.status).isEqualTo(ResultStatus.KO);
    }

    @Test
    void shouldHandleTimeout() throws UnknownHostException, ExecutionException, InterruptedException, TimeoutException {
        final var port = new Random().nextInt(1000) + 8000;
        final var x = buildRequest(port, "1", "+", "2");

        final var result = newFixedThreadPool(1)
                .submit(() -> Client.sendRequest(x, Duration.ofSeconds(1)));

        final var actual = result.get(10, SECONDS);
        assertThat(actual.status).isEqualTo(ResultStatus.TIMEOUT);
    }

    private String serverAnswers(final int port, final String s) {
        try (final var serverSocket = new DatagramSocket(port)) {
            System.out.println("Listening...");

            final var receiveData = new byte[7];
            final var dp = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(dp);

            System.out.println("Listening UDP " + dp.getAddress() + "]");

            final var sendPacket = new DatagramPacket(s.getBytes(), s.getBytes().length, dp.getAddress(), dp.getPort());
            serverSocket.send(sendPacket);
            return new String(dp.getData());
        } catch (IOException e) {
            System.out.println("IO ERROR");
            return "error";
        }
    }
}
