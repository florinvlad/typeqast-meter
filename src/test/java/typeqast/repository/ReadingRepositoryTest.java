package typeqast.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.util.assertions.ReadingAssertions;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class ReadingRepositoryTest {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MeterRepository meterRepository;

    @AfterEach
    public void afterEach() {
        meterRepository.deleteAll();
        readingRepository.deleteAll();
    }

    @Test
    void addReadingTest() {

        Meter testMeter = meterRepository.save(new Meter());

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setMeter(testMeter);

        Reading resultReading = readingRepository.save(reading);

        ReadingAssertions.execute(reading, resultReading);

        Assertions.assertTrue(true);

    }

    @Test
    void updateReadingTest() {

        Meter testMeter = meterRepository.save(new Meter());

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setMeter(testMeter);

        Reading resultReading = readingRepository.save(reading);

        ReadingAssertions.execute(reading, resultReading);

        reading = new Reading(2001, Month.MAY, 2345L);
        reading.setMeter(testMeter);

        resultReading = readingRepository.save(reading);

        ReadingAssertions.execute(reading, resultReading);

        Optional<Reading> resultReading2 = readingRepository.findOne(Example.of(reading));

        Assertions.assertTrue(resultReading2.isPresent());

        ReadingAssertions.execute(reading, resultReading2.get());
    }

    @Test
    void getReadingsTest() {

        Meter testMeter = meterRepository.save(new Meter());

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setMeter(testMeter);

        Reading resultReading = readingRepository.save(reading);

        ReadingAssertions.execute(reading,resultReading);

        Reading reading2 = new Reading(2001, Month.MAY, 1111L);
        reading2.setMeter(testMeter);

        resultReading = readingRepository.save(reading2);

        ReadingAssertions.execute(reading2,resultReading);

        List<Reading> readingResultList = readingRepository.findAll();

        Assertions.assertNotNull(readingResultList);
        Assertions.assertEquals(2, readingResultList.size());

    }

}
