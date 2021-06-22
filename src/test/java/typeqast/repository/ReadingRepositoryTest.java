package typeqast.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.util.assertions.ReadingAssertions;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReadingRepositoryTest {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MeterRepository meterRepository;

    @After
    public void afterTest() {
        meterRepository.deleteAll();
        readingRepository.deleteAll();
    }

    @Test
    public void addReadingTest() {

        Meter testMeter = meterRepository.save(new Meter());

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setMeter(testMeter);

        Reading resultReading = readingRepository.save(reading);

        ReadingAssertions.execute(reading, resultReading);

    }

    @Test
    public void updateReadingTest() {

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

        Assert.assertTrue(resultReading2.isPresent());

        ReadingAssertions.execute(reading, resultReading2.get());
    }

    @Test
    public void getReadingsTest() {

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

        Assert.assertNotNull(readingResultList);
        Assert.assertEquals(readingResultList.size(), 2);

    }

}
