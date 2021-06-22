package typeqast.service;

import typeqast.entities.AggregateReading;
import typeqast.entities.Reading;

import java.math.BigInteger;
import java.util.List;

public interface ReadingService {

    Reading addReading(Reading reading, BigInteger meterId);

    Reading updateReading(Reading reading, BigInteger meterId);

    List<Reading> getReadings();

    AggregateReading getAggregateReadings(Integer year, BigInteger meterId);

}
