package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.entities.exception.ReadingNotFoundException;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;
import typeqast.service.ReadingService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link ReadingService}
 */
@Service
public class ReadingServiceImpl implements ReadingService {

    private static Logger logger = LoggerFactory.getLogger(ReadingServiceImpl.class);

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MeterRepository meterRepository;

    /**
     * Implementation for {@link ReadingService#addReading(Reading, BigInteger)}
     */
    @Override
    public Reading addReading(Reading reading, BigInteger meterId) throws MeterNotFoundException {

        logger.info("Received add reading request");

        Reading saveReading;

        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!resultMeter.isPresent()) {
            throw new MeterNotFoundException();
        }

        reading.setMeter(resultMeter.get());
        saveReading = readingRepository.save(reading);

        return saveReading;

    }

    /**
     * Implementation for {@link ReadingService#updateReading(Reading, BigInteger)}
     */
    @Override
    public Reading updateReading(Reading reading, BigInteger meterId) throws MeterNotFoundException, ReadingNotFoundException {

        logger.info("Received update reading request");

        Reading saveReading;

        BigInteger id = reading.getId();

        Optional<Reading> resultReading = readingRepository.findOne(Example.of(new Reading(id)));
        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!resultMeter.isPresent()) {
            throw new MeterNotFoundException();
        }

        if (!resultReading.isPresent()) {
            throw new ReadingNotFoundException();
        }

        reading.setMeter(resultMeter.get());
        saveReading = readingRepository.save(reading);

        return saveReading;
    }

    /**
     * Implementation for {@link ReadingService#getReadings()}
     */
    @Override
    public List<Reading> getReadings() {
        logger.info("Received get readings request");
        return readingRepository.findAll();
    }

}
