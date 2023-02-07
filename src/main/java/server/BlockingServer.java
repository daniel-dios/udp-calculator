package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import server.parser.Request;
import server.parser.RequestParser;
import server.secrets.Secret;
import server.service.CalculatorService;
import shared.Port;

import static shared.Constants.KO;

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
            try (final var socket = new DatagramSocket(port.getValue())) {
                System.out.printf(
                        "%n%nAttending requests on %s:%d hold ctr+c to exit.%n",
                        InetAddress.getLocalHost().getHostAddress(),
                        port.getValue()
                );

                final var receiveData = new byte[7];
                final var dp = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(dp);
                final var clientAddress = dp.getAddress();
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.printf("Received %d bytes from %s %n", dp.getLength(), clientAddress);

                requestParser
                        .parse(dp.getData())
                        .ifPresentOrElse(
                                it -> answerLogic(socket, dp, clientAddress, it),
                                () -> answerKO(socket, dp, clientAddress)
                        );
            } catch (AnswerException e) {
                System.out.printf("Problem answering to client %s cause:%s %n", e.address, e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
            System.out.println("---------------------------------------------------------------------------------------");
        }
    }

    private void answerKO(final DatagramSocket serverSocket, final DatagramPacket dp, final InetAddress clientAddress) {
        try {
            System.out.printf("La operacion recibida de %s no es valida %s, respondiendo con un KO.. %n", clientAddress, new String(dp.getData()));
            serverSocket.send(new DatagramPacket(KO.getBytes(), KO.getBytes().length, clientAddress, dp.getPort()));
        } catch (IOException e) {
            throw new AnswerException(e, dp.getAddress());
        }
    }

    private void answerLogic(
            final DatagramSocket serverSocket,
            final DatagramPacket dp,
            final InetAddress clientAddress,
            final Request rq
    ) {
        System.out.printf("La operacion recibida de %s es %s %n", clientAddress, rq);
        try {
            final var result = calculator.calculate(rq.operation(), rq.first(), rq.second());
            final var secretWithResult = secret.applyTo(result);
            System.out.printf("Enviando respuesta a %s %s -> val calculado %s + secreto %s, total: %s %n", clientAddress, rq, result, secret, secretWithResult);
            final var sendPacket = new DatagramPacket(secretWithResult.asBytes(), secretWithResult.asBytes().length, clientAddress, dp.getPort());
            serverSocket.send(sendPacket);
            System.out.printf("Respuesta enviada a %s %s -> %s %n", clientAddress, rq, secretWithResult);
        } catch (CalculatorService.CalculatorInputException e) {
            answerKO(serverSocket, dp, clientAddress);
        } catch (IOException e) {
            throw new AnswerException(e, dp.getAddress());
        }
    }

    private static class AnswerException extends RuntimeException {
        private final InetAddress address;

        public AnswerException(final IOException e, final InetAddress address) {
            super(e);
            this.address = address;
        }
    }
}
