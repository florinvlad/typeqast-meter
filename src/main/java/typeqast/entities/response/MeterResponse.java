package typeqast.entities.response;

import org.springframework.http.HttpStatus;
import typeqast.entities.Meter;

/**
 * Wrapper object for {@link Meter}
 */
public class MeterResponse {

    private Meter meter;
    private HttpStatus status;

    public MeterResponse(Meter meter, HttpStatus status) {
        this.meter = meter;
        this.status = status;
    }

    public MeterResponse() {

    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
