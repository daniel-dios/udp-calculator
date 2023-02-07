package client;

import java.time.Duration;

import static client.ResultStatus.KO;
import static client.ResultStatus.OK;
import static client.ResultStatus.TIMEOUT;

public class Result {

    public final ResultStatus status;
    private final String answer;

    private Result(final ResultStatus status, final String answer) {
        this.status = status;
        this.answer = answer;
    }

    public static Result ok(final String value) {
        return new Result(OK, value);
    }

    public static Result ko(final String s) {
        return new Result(KO, "BAD_REQUEST/SERVER_ERROR");
    }

    public static Result timeout(final Duration timeout) {
        return new Result(TIMEOUT, String.format("After %d millis.", timeout.toMillis()));
    }

    public String answer() {
        return this.answer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Result result = (Result) o;

        if (status != result.status) return false;
        return answer.equals(result.answer);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + answer.hashCode();
        return result;
    }
}
