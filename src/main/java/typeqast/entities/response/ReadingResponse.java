package typeqast.entities.response;

import org.springframework.http.HttpStatus;
import typeqast.entities.Reading;

/**
 * Wrapper object for {@link Reading}
 */
public class ReadingResponse {

    private Reading reading;
    private HttpStatus status;

    public ReadingResponse(Reading reading, HttpStatus status) {
        this.reading = reading;
        this.status = status;
    }

    public ReadingResponse() {

    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
