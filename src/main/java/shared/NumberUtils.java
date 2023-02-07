package shared;

import java.util.Optional;

public class NumberUtils {
    public static Optional<Integer> validateRange(final String arg, final int min, final int max) {
        try {
            final int number = Integer.parseInt(arg);
            if (number < min || number > max) {
                System.out.printf("%s is out of [%d, %d] range.%n", arg, min, max);
                return Optional.empty();
            }
            return Optional.of(number);
        } catch (NumberFormatException e) {
            System.out.printf("%s is not a number.%n", arg);
            return Optional.empty();
        }
    }
}
