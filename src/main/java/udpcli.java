import client.Client;
import client.ClientParameters;
import client.Result;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Optional;

public class udpcli {

    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    public static void main(String[] args) {
        ClientParameters
                .parse(args)
                .ifPresentOrElse(
                        udpcli::sendRequest,
                        udpcli::printInstructions
                );
    }

    public static Optional<Result> sync(String[] args) {
        return ClientParameters.parse(args)
                .map(params -> Client.sendRequest(params, TIMEOUT));
    }

    private static void sendRequest(final ClientParameters params) {
        final var result = Client.sendRequest(params, TIMEOUT);
        switch (result.status) {
            case OK:
                System.out.println("El valor recibido es: " + result.answer());
                break;
            case TIMEOUT:
                System.out.printf("No se recibe nada por el socket despues del timeout de %ds.%n", TIMEOUT.toSeconds());
                break;
            case KO:
                System.out.printf("El servidor ha respondido con KO reason %s.%n", result.answer());
                break;
        }
    }

    private static void printInstructions() {
        try {
            System.out.println(
                    "\nEl formato correcto es: " +
                            "\nudpcli <ip_address_servidor> <port_servidor> <numero> <simbolo> <numero>" +
                            "\n\t<ip_adress_servidor> debe ser la IP del servidor UDP." +
                            "\n\t<port_servidor> debe ser el puerto del servidor UDP." +
                            "\n\t<numero>> debe ser un entero entre 0 y 255. Para division no es valido 0 en el segundo numero." +
                            "\n\t<simbolo> para suma, '+'; resta, '-'; multiplicacion 'x', 'X' o '*' y division ':' o '/'." +
                            "\n" +
                            "\nEjemplo:" +
                            "\nudpcli " + InetAddress.getLocalHost().getHostAddress() + " 8081 1 x 2" +
                            "\njava udpcli " + InetAddress.getLocalHost().getHostAddress() + " 8081 1 x 2"
            );
        } catch (UnknownHostException e) {
            // no op
        }
    }
}
