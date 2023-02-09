import server.BlockingServer;
import server.ServerParameters;
import server.operation.OperationParser;
import server.operation.resolver.OperationResolver;

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
        new BlockingServer(params.getSecret(), new OperationParser(), new OperationResolver())
                .startListeningForever(params.getPort());
    }

    private static void printInstructions() {
        System.out.println();
        System.out.println("Correct format is:");
        System.out.println("udpser <server_port> <secreto>");
        System.out.println();
        System.out.println("\t<server_port> must be an unused UDP port.");
        System.out.println("\t<secret> must be a number (integer) of the range [0, 255].");
        System.out.println();
        System.out.println("Example:");
        System.out.println("udpser 8081 3");
        System.out.println("java udpser 8081 3");
    }
}
