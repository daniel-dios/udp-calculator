import client.Client;
import client.ClientParameters;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        final Optional<ClientParameters> maybeParams = ClientParameters.parse(args);
        if (maybeParams.isEmpty()) {
            System.out.println("Va sin tildes:");
            System.out.println("El formato correcto es: udpcli <ip_address_servidor> <port_servidor> <numero> <simbolo> <numero>");
            System.out.println("Los simbolos son para suma '+', resta '-', multiplicacion 'x', 'X' o '*' y division ':' o '/'");
            System.out.println("Los numeros deben ser valores enteros entre 0 y 255");
            System.out.println("Para la division el segundo numero no podra ser 0.");
            return;
        }

        final var bytes = Client.sendRequest(maybeParams.get());

        if (bytes.isPresent()) {
            System.out.println("El valor recibido es: " + bytes.get());
        } else {
            System.out.println("No se recibe nada por el socket.");
        }

    }
}
