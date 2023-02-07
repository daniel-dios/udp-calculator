package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import server.service.CalculatorService;
import shared.Port;

public class BlockingServer {

    private final Secret secret;
    private final RequestParser requestParser;
    private final CalculatorService calculator;

    public BlockingServer(
            final Secret secret,
            final RequestParser requestParser,
            final CalculatorService calculator
    ) {
        this.secret = secret;
        this.requestParser = requestParser;
        this.calculator = calculator;
    }

    public void startListeningForever(final Port port) {
        while (true) {
            try (final var serverSocket = new DatagramSocket(port.getValue())) {
                System.out.printf("Attending requests on port: %d and address: %s %n", port.getValue(), InetAddress.getLocalHost());

                final var receiveData = new byte[7];
                final var dp = new DatagramPacket(receiveData, receiveData.length);

                serverSocket.receive(dp);
                System.out.printf("Received data %d bytes from %s %n", dp.getLength(), dp.getAddress());

                final var request = requestParser.parse(dp.getData());
                if (request.isPresent()) {
                    final var result = calculator.calculate(secret, request.get().operation(), request.get().first(), request.get().second());
                    final var sendPacket = new DatagramPacket(result.asBytes(), result.asBytes().length, dp.getAddress(), dp.getPort());
                    serverSocket.send(sendPacket);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
