import client.Client;
import client.ClientParameters;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class udpcli {

    public static void main(String[] args) {
        ClientParameters
                .parse(args)
                .ifPresentOrElse(
                        udpcli::sendRequest,
                        udpcli::printInstructions
                );
    }

    public static String sync(String[] args) {
        return ClientParameters.parse(args)
                .flatMap(Client::sendRequest)
                .orElse("KO");
    }

    private static void sendRequest(final ClientParameters params) {
        Client.sendRequest(params)
                .ifPresentOrElse(
                        it -> System.out.println("El valor recibido es: " + it),
                        () -> System.out.println("No se recibe nada por el socket después de 10s.")
                );
    }

    private static void printInstructions() {
        try {
            System.out.println(
                    "\nEl formato correcto es: " +
                            "\nudpcli <ip_address_servidor> <port_servidor> <numero> <simbolo> <numero>" +
                            "\n\t<ip_adress_servidor> debe ser la IP del servidor UDP." +
                            "\n\t<port_servidor> debe ser el puerto del servidor UDP." +
                            "\n\t<numero>> debe ser un entero entre 0 y 255." +
                            "\n\t<simbolo> para suma, '+'; resta, '-'; multiplicacion 'x', 'X' o '*' y division ':' o '/'." +
                            "\n" +
                            "\nEjemplo:" +
                            "\nudpcli " + InetAddress.getLocalHost().getHostAddress() + " 8081 1 x 2"
            );
        } catch (UnknownHostException e) {
            // no op
        }
    }
}
