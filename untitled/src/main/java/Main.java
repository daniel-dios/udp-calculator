import client.ClientParameters;

public class Main {
    public static void main(String[] args) {

        if (!ClientParameters.parse(args).isPresent()) {
            System.out.println("El formato correcto es: udpcli <ip_address_servidor> <port_servidor> <número> <simbolo> <número>");
            System.out.println("Los símbolos son para suma '+', resta '-', multiplicación 'x' y división ':'");
            System.out.println("Los números deben ser valores enteros entre 0 y 255");
            System.out.println("Para la divisón el segundo número no podrá ser 0.");
            return;
        }

        
    }
}
