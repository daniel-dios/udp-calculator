package server;

import client.Operation;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Objects;
import server.service.CalculatorService;
import shared.OperableNumber;
import shared.Port;

import static java.util.Arrays.stream;

public class BlockingServer {

    private final Secret secret;
    private final CalculatorService calculator;

    public BlockingServer(final Secret secret, final CalculatorService calculator) {
        this.secret = secret;
        this.calculator = calculator;
    }

    public void startListeningForever(final Port port) {
        while (true) {
            try (final var serverSocket = new DatagramSocket(port.getValue())) {
                System.out.println("Listening...");

                final var receiveData = new byte[7];
                final var dp = new DatagramPacket(receiveData, receiveData.length);

                serverSocket.receive(dp);

                System.out.println("[UDP " + dp.getAddress() + "]");

                final var receivedText = new String(dp.getData()).trim();

                // TODO move all this logic to another service
                final var operation = stream(Operation.values()).filter(s -> receivedText.contains(Objects.requireNonNull(s.getSymbol()))).findFirst();
                if (operation.isPresent()) {
                    final var splitByOperation = receivedText.split(Objects.requireNonNull(operation.get().getSymbol()));
                    if (splitByOperation.length != 2) {
                        System.out.println("Content is not NUMBERoperationNUMBER");
                        return;
                    }
                    final var first = OperableNumber.parse(splitByOperation[0]);
                    final var second = OperableNumber.parse(splitByOperation[1]);

                    if (first.isPresent() && second.isPresent()) {
                        final var result = calculator.calculate(secret, operation.get(), first.get(), second.get());
                        final var sendPacket = new DatagramPacket(result.asBytes(), result.asBytes().length, dp.getAddress(), dp.getPort());
                        serverSocket.send(sendPacket);
                    }
                }
                // TODO handle this properly
            } catch (IOException e) {
                System.out.println("IO ERROR");
            }
        }
    }
}
