package server.secrets;

public class SecretResult {
    private final int value;

    public SecretResult(final int value) {
        this.value = value;
    }

    public byte[] asBytes() {
        return String.valueOf(value).getBytes();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}