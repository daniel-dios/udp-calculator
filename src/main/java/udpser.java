import server.BlockingServer;
import server.ServerParameters;

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
        new BlockingServer(params.getSecret())
                .startListeningForever(params.getPort());
    }

    private static void printInstructions() {
        System.out.println(
                "\nEl formato correcto es: " +
                        "\nudpser <port_servidor> <secreto>" +
                        "\n<port_servidor> debe ser un puerto UDP valido que no esté usado." +
                        "\n<secreto> debe ser un valor entero entre 0 y 255." +
                        "\n(Sin tildes intencionadamente)"
        );
    }
}
