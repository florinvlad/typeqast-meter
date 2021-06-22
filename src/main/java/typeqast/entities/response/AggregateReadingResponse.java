package typeqast.entities.response;

import org.springframework.http.HttpStatus;
import typeqast.entities.AggregateReading;

/**
 * Wrapper object for {@link AggregateReading}
 */
public class AggregateReadingResponse {

    private AggregateReading aggregateReading;
    private HttpStatus status;

    public AggregateReadingResponse(AggregateReading aggregateReading, HttpStatus status) {
        this.aggregateReading = aggregateReading;
        this.status = status;
    }

    public AggregateReadingResponse() {

    }

    public AggregateReading getAggregateReading() {
        return aggregateReading;
    }

    public void setAggregateReading(AggregateReading aggregateReading) {
        this.aggregateReading = aggregateReading;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
