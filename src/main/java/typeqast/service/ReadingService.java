package typeqast.service;

import typeqast.entities.Reading;
import typeqast.entities.exception.ReadingNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface ReadingService {

    Reading addReading(Reading reading, BigInteger meterId);

    Reading updateReading(Reading reading, BigInteger meterId) throws ReadingNotFoundException;

    List<Reading> getReadings();

}
