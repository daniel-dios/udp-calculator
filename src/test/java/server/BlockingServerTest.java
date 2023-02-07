package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.mockito.internal.verification.Times;
import server.service.CalculatorResult;
import shared.OperableNumber;
import client.Operation;
import org.junit.jupiter.api.Test;
import server.service.CalculatorService;
import shared.Port;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlockingServerTest {

    @Test
    void shouldCallCalculatorService() throws ExecutionException, InterruptedException, TimeoutException {
        final var calculator = mock(CalculatorService.class);
        final var secret = new Secret(4);
        when(calculator.calculate(secret, Operation.MUL, buildNumber(3), buildNumber(2)))
                .thenReturn(new CalculatorResult());
        final var server = new BlockingServer(secret, calculator);

        final var port = 9000;
        newFixedThreadPool(1)
                .submit(() -> server.startListeningForever(Port.parse(String.valueOf(port)).get()));

        final var updCall = newFixedThreadPool(1)
                .submit(() -> {
                    try {
                        sendUDP(port, "3x2");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        updCall.get(30, TimeUnit.SECONDS);
        verify(calculator, new Times(1))
                .calculate(secret, Operation.MUL, buildNumber(3), buildNumber(2));
    }

    private Optional<String> sendUDP(final int port, final String operation) throws IOException {
        try (final var ds = new DatagramSocket()) {
            ds.setSoTimeout(10000);
            final var send = operation.getBytes();
            final var datagramPacket = new DatagramPacket(send, send.length, InetAddress.getLocalHost(), port);

            ds.send(datagramPacket);

            final var received = new DatagramPacket(new byte[5], 5); // -255 to 65280 are 5 characters as max.
            ds.receive(received);
            return Optional.of(new String(received.getData()).trim());
        }
    }

    private OperableNumber buildNumber(int number) {
        return OperableNumber.parse(String.valueOf(number)).get();
    }
}
