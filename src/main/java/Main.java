import client.Client;
import client.ClientParameters;

public class Main {
    public static void main(String[] args) {
        ClientParameters
                .parse(args)
                .ifPresentOrElse(
                        Main::sendRequest,
                        Main::printInstructions
                );
    }

    private static void sendRequest(final ClientParameters params) {
        Client
                .sendRequest(params)
                .ifPresentOrElse(
                        it -> System.out.println("El valor recibido es: " + it),
                        () -> System.out.println("No se recibe nada por el socket.")
                );
    }

    private static void printInstructions() {
        System.out.println("Va sin tildes :( :");
        System.out.println("El formato correcto es: udpcli <ip_address_servidor> <port_servidor> <numero> <simbolo> <numero>");
        System.out.println("Los numeros deben ser valores enteros entre 0 y 255");
        System.out.println("Los simbolos son: para suma,'+'; resta, '-'; multiplicacion 'x', 'X' o '*' y division ':' o '/'");
        System.out.println("Para la division el segundo numero no podra ser 0.");
    }
}
