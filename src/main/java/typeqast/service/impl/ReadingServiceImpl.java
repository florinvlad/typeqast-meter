package typeqast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.ReadingProcessor;
import typeqast.entities.AggregateReading;
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

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    @Qualifier("aggregateReading")
    private ReadingProcessor readingProcessor;

    @Override
    public Reading addReading(Reading reading, BigInteger meterId) {

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
        return readingRepository.findAll();
    }

    @Override
    public AggregateReading getAggregateReadings(Integer year, BigInteger meterId) {

        Reading queryReading = new Reading();
        queryReading.setYear(year);

        if (meterId != null) {
            Optional<Meter> meterResult = meterRepository.findOne(Example.of(new Meter(meterId)));
            if (meterResult.isPresent()) {
                queryReading.setMeter(meterResult.get());
            }
        }

        List<Reading> readingList = readingRepository.findAll(Example.of(queryReading));

        Long total = readingProcessor.process(readingList);

        AggregateReading aggregateReading = new AggregateReading(year, total);

        return aggregateReading;

    }

}
