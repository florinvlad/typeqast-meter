package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;
import typeqast.service.ReadingService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingServiceImpl implements ReadingService {

    private static Logger logger = LoggerFactory.getLogger(ReadingServiceImpl.class);

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Override
    public Reading addReading(Reading reading, BigInteger meterId) {

        logger.info("Received add reading request");

        Reading saveReading = null;

        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (resultMeter.isPresent()) {
            reading.setMeter(resultMeter.get());
            saveReading = readingRepository.save(reading);
        }

        return saveReading;

    }

    @Override
    public Reading updateReading(Reading reading, BigInteger meterId) {

        logger.info("Received update reading request");

        Reading saveReading = null;

        BigInteger id = reading.getId();

        if (id != null) {
            Optional<Reading> resultReading = readingRepository.findOne(Example.of(new Reading(id)));
            Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

            if (resultMeter.isPresent() && resultReading.isPresent()) {
                reading.setMeter(resultMeter.get());
                saveReading = readingRepository.save(reading);
            }
        }

        return saveReading;
    }

    @Override
    public List<Reading> getReadings() {
        logger.info("Received get readings request");
        return readingRepository.findAll();
    }

}
