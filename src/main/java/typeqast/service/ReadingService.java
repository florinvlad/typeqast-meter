package typeqast.service;

import typeqast.entities.dto.ReadingDTO;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.entities.exception.ReadingNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface ReadingService {

    ReadingDTO addReading(ReadingDTO reading, BigInteger meterId) throws MeterNotFoundException;

    ReadingDTO updateReading(ReadingDTO reading, BigInteger meterId) throws MeterNotFoundException, ReadingNotFoundException;

    List<ReadingDTO> getReadings();

}
