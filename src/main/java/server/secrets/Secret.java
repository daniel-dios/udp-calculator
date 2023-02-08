package server.secrets;

import java.util.Optional;
import server.NumberUtils;
import server.operation.resolver.OperationResult;

public class Secret {
    private final int value;

    public Secret(final int value) {
        this.value = value;
    }

    public static Optional<Secret> parse(final String arg) {
        return NumberUtils.validateRange(arg, 0, 255).map(Secret::new);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Secret secret = (Secret) o;

        return value == secret.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public SecretResult applyTo(final OperationResult result) {
        return new SecretResult(this.value + result.value());
    }
}
