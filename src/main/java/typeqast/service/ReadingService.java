package typeqast.service;

import typeqast.entities.Reading;
import typeqast.entities.response.AggregateReadingResponse;
import typeqast.entities.response.ReadingResponse;

import java.math.BigInteger;
import java.util.List;

public interface ReadingService {

    ReadingResponse addReading(Reading reading, BigInteger meterId);

    ReadingResponse updateReading(Reading reading, BigInteger meterId);

    List<Reading> getReadings();

    AggregateReadingResponse getAggregateReadings(Integer year, BigInteger meterId);

}
