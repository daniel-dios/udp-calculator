package client.response;

import java.time.Duration;

import static client.response.Status.KO;
import static client.response.Status.OK;
import static client.response.Status.TIMEOUT;

public class ServerResponse {

    public final Status status;
    private final String answer;

    private ServerResponse(final Status status, final String answer) {
        this.status = status;
        this.answer = answer;
    }

    public static ServerResponse ok(final String value) {
        return new ServerResponse(OK, value);
    }

    public static ServerResponse ko(final String s) {
        return new ServerResponse(KO, s);
    }

    public static ServerResponse timeout(final Duration timeout) {
        return new ServerResponse(TIMEOUT, String.format("After %d millis.", timeout.toMillis()));
    }

    public String answer() {
        return this.answer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ServerResponse serverResponse = (ServerResponse) o;

        if (status != serverResponse.status) return false;
        return answer.equals(serverResponse.answer);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + answer.hashCode();
        return result;
    }
}
