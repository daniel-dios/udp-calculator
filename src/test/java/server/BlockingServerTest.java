package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import server.parser.Request;
import server.parser.RequestParser;
import server.secrets.Secret;
import server.service.CalculatorService;
import shared.OperableNumber;
import shared.Operation;
import shared.OperationResult;
import shared.Port;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shared.Operation.MUL;

public class BlockingServerTest {

    @Test
    void shouldCallCalculatorService() throws ExecutionException, InterruptedException, TimeoutException {
        final var calculator = mock(CalculatorService.class);
        final var parser = mock(RequestParser.class);
        final var secret = new Secret(4);
        final var operationAsText = "3x2";
        final var operation = MUL;
        final var first = buildNumber(3);
        final var second = buildNumber(2);
        when(calculator.calculate(operation, first, second)).thenReturn(new OperationResult(10));
        when(parser.parse(any())).thenReturn(Optional.of(new Request(operation, first, second)));
        final var server = new BlockingServer(secret, parser, calculator);
        final var port = getPort(String.valueOf(9000));

        newFixedThreadPool(1).submit(() -> server.startListeningForever(port));
        final var updCall = newFixedThreadPool(1).submit(() -> sendUDP(port.getValue(), operationAsText));

        updCall.get(30, TimeUnit.SECONDS);
        verify(calculator, new Times(1)).calculate(operation, first, second);
    }

    @Test
    void shouldAnswerProperly() throws ExecutionException, InterruptedException, TimeoutException {
        final var calculator = mock(CalculatorService.class);
        final var parser = mock(RequestParser.class);
        final var secret = new Secret(5);
        final var expectedResult = 7;
        final var operation = Operation.DIV;
        final var first = buildNumber(4);
        final var second = buildNumber(2);
        final var operationAsText = "4:2";
        when(calculator.calculate(operation, first, second)).thenReturn(new OperationResult(4 / 2));
        when(parser.parse(any())).thenReturn(Optional.of(new Request(operation, first, second)));
        final var server = new BlockingServer(secret, parser, calculator);
        final var port = getPort("8080");

        newFixedThreadPool(1).submit(() -> server.startListeningForever(port));
        final var updCall = newFixedThreadPool(1).submit(() -> sendUDP(port.getValue(), operationAsText));

        final var answer = updCall.get(30, TimeUnit.SECONDS);
        verify(calculator, new Times(1)).calculate(operation, first, second);
        assertThat(answer).isPresent().get().extracting(String::trim).isEqualTo(String.valueOf(expectedResult));
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

    private Port getPort(final String arg) {
        return Port.parse(arg).get();
    }
}
