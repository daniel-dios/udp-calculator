import client.ClientParameters;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        if (!ClientParameters.parse(args).isPresent()) {
            System.out.println("Va sin tildes:");
            System.out.println("El formato correcto es: udpcli <ip_address_servidor> <port_servidor> <numero> <simbolo> <numero>");
            System.out.println("Los simbolos son para suma '+', resta '-', multiplicacion 'x', 'X' o '*' y division ':' o '/'");
            System.out.println("Los numeros deben ser valores enteros entre 0 y 255");
            System.out.println("Para la division el segundo numero no podra ser 0.");
            return;
        }

        try (DatagramSocket ds = new DatagramSocket()) {
            ds.setSoTimeout(10000);
            final byte[] receiveData = new byte[BigInteger.valueOf(65280).toByteArray().length];


        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
