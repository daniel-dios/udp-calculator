import client.ClientParameters;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        final Optional<ClientParameters> maybeParams = ClientParameters.parse(args);
        if (!maybeParams.isPresent()) {
            System.out.println("Va sin tildes:");
            System.out.println("El formato correcto es: udpcli <ip_address_servidor> <port_servidor> <numero> <simbolo> <numero>");
            System.out.println("Los simbolos son para suma '+', resta '-', multiplicacion 'x', 'X' o '*' y division ':' o '/'");
            System.out.println("Los numeros deben ser valores enteros entre 0 y 255");
            System.out.println("Para la division el segundo numero no podra ser 0.");
            return;
        }

        final Optional<byte[]> bytes = sendRequest(maybeParams.get());

        if (bytes.isPresent()) {
            System.out.println("El valor recibido es: " + new BigInteger(bytes.get()));
        } else {
            System.out.println("No se recibe nada por el socket.");
        }

    }

    private static Optional<byte[]> sendRequest(final ClientParameters params) {
        try (DatagramSocket ds = new DatagramSocket()) {
            ds.setSoTimeout(10000);
            final byte[] send = params.toRequest();
            final DatagramPacket datagramPacket = new DatagramPacket(send, send.length, params.address(), params.port());

            ds.send(datagramPacket);

            final byte[] receiveData = new byte[BigInteger.valueOf(65280).toByteArray().length];
            final DatagramPacket received = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(received);
            return Optional.of(received.getData());
        } catch (SecurityException e) {
            System.out.println(" – if a security manager exists and its checkMulticast or checkConnect method doesn't allow the send.");
            return Optional.empty();
        } catch (PortUnreachableException e) {
            System.out.println(" – may be thrown if the socket is connected to a currently unreachable destination. Note, there is no guarantee that the exception will be thrown.");
            return Optional.empty();
        } catch (IllegalBlockingModeException e) {
            System.out.println(" – if this socket has an associated channel, and the channel is in non-blocking mode.");
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            System.out.println(" – if the socket is connected, and connected address and packet address differ.");
            return Optional.empty();
        } catch (SocketTimeoutException e) {
            System.out.println(" – if setSoTimeout was previously called and the timeout has expired.");
            return Optional.empty();
        } catch (IOException e) {
            System.out.println(" – if an I/O error occurs.");
            return Optional.empty();
        }
    }
}
