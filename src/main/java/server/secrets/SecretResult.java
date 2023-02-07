package server.secrets;

public class SecretResult {
    private final int value;

    public SecretResult(final int value) {
        this.value = value;
    }

    public byte[] asBytes() {
        return String.valueOf(value).getBytes();
    }
}