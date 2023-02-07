package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import server.parser.Request;
import server.parser.RequestParser;
import server.secrets.Secret;
import server.service.CalculatorService;

import static contract.GlobalConstants.KO;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static server.Builders.number;
import static server.Builders.port;
import static server.Builders.result;
import static server.OperationSymbol.DIV;
import static server.OperationSymbol.MUL;

public class BlockingServerTest {

    public static final Times ONCE = new Times(1);
    public static final Times NEVER = new Times(0);

    @Test
    void shouldCallCalculatorService() throws ExecutionException, InterruptedException, TimeoutException, CalculatorService.CalculatorInputException {
        final var calculator = mock(CalculatorService.class);
        final var parser = mock(RequestParser.class);
        final var secret = new Secret(4);
        final var operationAsText = "3x2";
        final var operation = MUL;
        final var first = number(3);
        final var second = number(2);
        when(calculator.calculate(operation, first, second)).thenReturn(result(10));
        when(parser.parse(any())).thenReturn(Optional.of(new Request(operation, first, second)));
        final var server = new BlockingServer(secret, parser, calculator);
        final Port port = port("9000");

        newFixedThreadPool(1).submit(() -> server.startListeningForever(port));
        final var updCall = newFixedThreadPool(1).submit(() -> sendUDP(port.getValue(), operationAsText));

        updCall.get(30, SECONDS);
        verify(calculator, ONCE).calculate(operation, first, second);
    }

    @Test
    void shouldAnswerProperly() throws ExecutionException, InterruptedException, TimeoutException, CalculatorService.CalculatorInputException {
        final var calculator = mock(CalculatorService.class);
        final var parser = mock(RequestParser.class);
        final var secret = new Secret(5);
        final var expectedResult = 7;
        final var operation = DIV;
        final var first = number(4);
        final var second = number(2);
        final var operationAsText = "4:2";
        when(parser.parse(any())).thenReturn(Optional.of(new Request(operation, first, second)));
        when(calculator.calculate(operation, first, second)).thenReturn(result(4 / 2));
        final var server = new BlockingServer(secret, parser, calculator);
        final var port = port("8080");

        newFixedThreadPool(1).submit(() -> server.startListeningForever(port));
        final var updCall = newFixedThreadPool(1).submit(() -> sendUDP(port.getValue(), operationAsText));

        final var answer = updCall.get(30, SECONDS);
        verify(calculator, ONCE).calculate(operation, first, second);
        assertThat(answer).isPresent().get().extracting(String::trim).isEqualTo(String.valueOf(expectedResult));
    }

    @Test
    void shouldAnswerToWrongInputWithKO() throws ExecutionException, InterruptedException, TimeoutException, CalculatorService.CalculatorInputException {
        final var calculator = mock(CalculatorService.class);
        final var parser = mock(RequestParser.class);
        final var secret = new Secret(6);
        final var operationAsText = "4asd2";
        when(parser.parse(any())).thenReturn(Optional.empty());
        final var server = new BlockingServer(secret, parser, calculator);
        final var port = port("8081");

        newFixedThreadPool(1).submit(() -> server.startListeningForever(port));
        final var updCall = newFixedThreadPool(1).submit(() -> sendUDP(port.getValue(), operationAsText));

        final var answer = updCall.get(30, SECONDS);
        verify(calculator, NEVER).calculate(any(), any(), any());
        assertThat(answer).isPresent().get().extracting(String::trim).isEqualTo(KO);
    }

    @Test
    void shouldAnswerToDivZeroWithKO() throws ExecutionException, InterruptedException, TimeoutException, CalculatorService.CalculatorInputException {
        final var calculator = mock(CalculatorService.class);
        final var parser = mock(RequestParser.class);
        final var secret = new Secret(6);
        final var operationAsText = "4:0";
        final var first = number(4);
        final var second = number(0);
        final var operation = DIV;
        when(parser.parse(any())).thenReturn(Optional.of(new Request(operation, first, second)));
        when(calculator.calculate(operation, first, second)).thenThrow(new CalculatorService.CalculatorInputException("I can't divide by Zero"));
        final var server = new BlockingServer(secret, parser, calculator);
        final var port = port("8082");

        newFixedThreadPool(1).submit(() -> server.startListeningForever(port));
        final var updCall = newFixedThreadPool(1).submit(() -> sendUDP(port.getValue(), operationAsText));

        final var answer = updCall.get(30, SECONDS);
        verify(calculator, ONCE).calculate(operation, first, second);
        assertThat(answer).isPresent().get().extracting(String::trim).isEqualTo(KO);
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
}
