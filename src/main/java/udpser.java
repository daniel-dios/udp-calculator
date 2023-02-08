import server.BlockingServer;
import server.ServerParameters;
import server.operation.OperationParser;
import server.service.OperationService;

public class udpser {
    public static void main(String[] args) {
        ServerParameters
                .parse(args)
                .ifPresentOrElse(
                        udpser::listen,
                        udpser::printInstructions
                );
    }

    private static void listen(final ServerParameters params) {
        new BlockingServer(params.getSecret(), new OperationParser(), new OperationService())
                .startListeningForever(params.getPort());
    }

    private static void printInstructions() {
        System.out.println(
                "\nEl formato correcto es: " +
                        "\nudpser <port_servidor> <secreto>" +
                        "\n\t<port_servidor> debe ser un puerto UDP valido que no esté usado." +
                        "\n\t<secreto> debe ser un valor entero entre 0 y 255." +
                        "\nEjemplo:" +
                        "\nudpser 8081 3" +
                        "\njava udpser 8081 3"
        );
    }
}
