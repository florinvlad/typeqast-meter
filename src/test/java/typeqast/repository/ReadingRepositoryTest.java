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

        Assert.assertNotNull(resultReading.getId());
        Assert.assertEquals(reading.getYear(), resultReading.getYear());
        Assert.assertEquals(reading.getMonth(), resultReading.getMonth());
        Assert.assertEquals(reading.getValue(), resultReading.getValue());

    }

    @Test
    public void updateReadingTest() {

        Meter testMeter = meterRepository.save(new Meter());

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setMeter(testMeter);

        Reading resultReading = readingRepository.save(reading);

        Assert.assertNotNull(resultReading.getId());
        Assert.assertEquals(reading.getYear(), resultReading.getYear());
        Assert.assertEquals(reading.getMonth(), resultReading.getMonth());
        Assert.assertEquals(reading.getValue(), resultReading.getValue());

        reading = new Reading(2001, Month.MAY, 2345L);
        reading.setMeter(testMeter);

        resultReading = readingRepository.save(reading);

        Assert.assertNotNull(resultReading.getId());
        Assert.assertEquals(reading.getYear(), resultReading.getYear());
        Assert.assertEquals(reading.getMonth(), resultReading.getMonth());
        Assert.assertEquals(reading.getValue(), resultReading.getValue());

        Optional<Reading> resultReading2 = readingRepository.findOne(Example.of(reading));

        Assert.assertTrue(resultReading2.isPresent());
        Assert.assertNotNull(resultReading.getId());
        Assert.assertEquals(reading.getYear(), resultReading.getYear());
        Assert.assertEquals(reading.getMonth(), resultReading.getMonth());
        Assert.assertEquals(reading.getValue(), resultReading.getValue());
    }

    @Test
    public void getReadingsTest() {

        Meter testMeter = meterRepository.save(new Meter());

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setMeter(testMeter);

        Reading resultReading = readingRepository.save(reading);

        Assert.assertNotNull(resultReading);
        Assert.assertNotNull(resultReading.getId());
        Assert.assertEquals(reading.getYear(), resultReading.getYear());
        Assert.assertEquals(reading.getMonth(), resultReading.getMonth());
        Assert.assertEquals(reading.getValue(), resultReading.getValue());

        Reading reading2 = new Reading(2001, Month.MAY, 1111L);
        reading2.setMeter(testMeter);

        resultReading = readingRepository.save(reading2);

        Assert.assertNotNull(resultReading);
        Assert.assertNotNull(resultReading.getId());
        Assert.assertEquals(reading2.getYear(), resultReading.getYear());
        Assert.assertEquals(reading2.getMonth(), resultReading.getMonth());
        Assert.assertEquals(reading2.getValue(), resultReading.getValue());

        List<Reading> readingResultList = readingRepository.findAll();

        Assert.assertNotNull(readingResultList);
        Assert.assertEquals(readingResultList.size(), 2);

    }

}
