package typeqast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import typeqast.business.ReadingProcessor;
import typeqast.entities.AggregateReading;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.response.AggregateReadingResponse;
import typeqast.entities.response.ReadingResponse;
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
    public ReadingResponse addReading(Reading reading, BigInteger meterId) {

        ReadingResponse readingResponse = new ReadingResponse();

        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (resultMeter.isPresent()) {
            reading.setMeter(resultMeter.get());
            readingResponse.setReading(readingRepository.save(reading));
            readingResponse.setStatus(HttpStatus.CREATED);
        } else {
            readingResponse.setStatus(HttpStatus.NOT_FOUND);
        }

        return readingResponse;

    }

    @Override
    public ReadingResponse updateReading(Reading reading, BigInteger meterId) {

        ReadingResponse readingResponse = new ReadingResponse();

        BigInteger id = reading.getId();

        if (id != null) {
            Optional<Reading> resultReading = readingRepository.findOne(Example.of(new Reading(id)));
            Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

            if (resultMeter.isPresent() && resultReading.isPresent()) {
                reading.setMeter(resultMeter.get());
                readingResponse.setReading(readingRepository.save(reading));
                readingResponse.setStatus(HttpStatus.OK);
            } else {
                readingResponse.setStatus(HttpStatus.NOT_FOUND);
            }
        } else {
            readingResponse.setStatus(HttpStatus.BAD_REQUEST);
        }

        return readingResponse;
    }

    @Override
    public List<Reading> getReadings() {
        return readingRepository.findAll();
    }

    @Override
    public AggregateReadingResponse getAggregateReadings(Integer year, BigInteger meterId) {

        AggregateReadingResponse aggregateReadingResponse = new AggregateReadingResponse();

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

        aggregateReadingResponse.setAggregateReading(new AggregateReading(year, total));
        aggregateReadingResponse.setStatus(HttpStatus.OK);

        return aggregateReadingResponse;

    }

}
