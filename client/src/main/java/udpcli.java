import client.Client;
import client.params.Parameters;
import client.response.ServerResponse;
import java.time.Duration;
import java.util.Optional;

public class udpcli {

    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    public static void main(String[] args) {
        Parameters
                .parse(args)
                .ifPresentOrElse(
                        udpcli::sendRequest,
                        udpcli::printInstructions
                );
    }

    public static Optional<ServerResponse> sync(String[] args) {
        return Parameters.parse(args)
                .map(params -> Client.sendRequest(params, TIMEOUT));
    }

    private static void sendRequest(final Parameters params) {
        final var result = Client.sendRequest(params, TIMEOUT);
        switch (result.status) {
            case OK:
                System.out.println("El valor recibido es: " + result.answer());
                break;
            case TIMEOUT:
                System.out.printf("No se recibe nada por el socket despues del timeout de %ds.%n", TIMEOUT.toSeconds());
                break;
            case KO:
                System.out.printf("El servidor ha respondido con KO con reason: %s.%n", result.answer());
                break;
        }
    }

    // TODO find a nice way
    private static void printInstructions() {
        System.out.println();
        System.out.println("Correct format is: ");
        System.out.println("udpcli <server_ip_address> <server_port> <operation>");
        System.out.println("\t<server_ip_address> must be the UDP server IP.");
        System.out.println("\t<server_port> must be the UDP port.");
        System.out.println("\t<operation> must be the operation you want to send in format NsN:");
        System.out.println("\t\tN must be a number (integer) of the [0,255].");
        System.out.println("\t\ts it's the operation symbol");
        System.out.println("\t\taddition('+'), subtraction('-'), multiplication('x'), division(':')");
        System.out.println();
        System.out.println("Example/s:");
        System.out.println("udpcli 127.0.0.1 8081 1x2");
        System.out.println("java udpcli 127.0.0.1 8081 1x2");
    }
}
