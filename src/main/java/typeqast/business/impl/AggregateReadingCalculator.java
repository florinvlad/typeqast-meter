package typeqast.business.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import typeqast.business.ReadingProcessor;
import typeqast.entities.Reading;

import java.util.List;

@Component
@Qualifier("aggregateReading")
public class AggregateReadingCalculator implements ReadingProcessor {

    @Override
    public Long process(List<Reading> readings) {

        Long total = 0L;

        for (Reading reading : readings) {
            total += reading.getValue();
        }

        return total;
    }

}
