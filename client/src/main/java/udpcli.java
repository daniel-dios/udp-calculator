import client.Client;
import client.params.Parameters;
import client.response.ServerResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        System.out.println("El formato correcto es: ");
        System.out.println("udpcli <ip_address_servidor> <port_servidor> <operacion>");
        System.out.println("\t<ip_adress_servidor> debe ser la IP del servidor UDP.");
        System.out.println("\t<port_servidor> debe ser el puerto del servidor UDP.");
        System.out.println(
                "\t<operacion> debe ser un entero entre 0 y 255, seguido (sin espacio) de un simbolo de operacion:"
                        + "(suma, '+'; resta, '-'; multiplicacion 'x', y division ':' )"
                        + " y de otro numero de entre 0 y 255 [menos para la division, que sera de entre 1 y 255]"
        );
        System.out.println();
        System.out.println("Ejemplo:");
        System.out.println("udpcli " + getHostAddress() + " 8081 1x2");
        System.out.println("java udpcli " + getHostAddress() + " 8081 1x2");
    }

    private static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
}
