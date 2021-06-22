package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.business.ReadingProcessor;
import typeqast.business.impl.AggregateReadingCalculator;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.response.ReadingResponse;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;
import typeqast.service.impl.ReadingServiceImpl;
import typeqast.util.assertions.ReadingAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ReadingServiceTest {

    @TestConfiguration
    static class ServiceImplTestContextConfiguration {

        @Bean
        public ReadingService readingService() {
            return new ReadingServiceImpl();
        }

    }
    @TestConfiguration
    static class ReadingProcessorTestContextConfiguration {

        @Bean
        public ReadingProcessor readingProcessor() {
            return new AggregateReadingCalculator();
        }

    }


    @Autowired
    private ReadingService readingService;

    @Autowired
    @Qualifier("aggregateReading")
    private ReadingProcessor readingProcessor;

    @MockBean
    private ReadingRepository readingRepository;

    @MockBean
    private MeterRepository meterRepository;

    /**
     * Add reading unit test for {@link ReadingServiceImpl}
     */
    @Test
    public void addReadingTest() {

        Reading requestReading = new Reading(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Meter(BigInteger.valueOf(1))));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading);

        ReadingResponse readingResponse = readingService.addReading(requestReading, BigInteger.valueOf(1));

        Assert.assertNotNull("Result reading response should not be null ", readingResponse);
        Assert.assertNotNull("Result reading should not be null ", readingResponse.getReading());

        ReadingAssertions.execute(mockResultReading,readingResponse.getReading());

    }

    /**
     * Update reading unit test for {@link ReadingServiceImpl}
     */
    @Test
    public void updateReadingTest() {

        Reading requestReading = new Reading(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Meter(BigInteger.valueOf(1))));
        Mockito.when(readingRepository.save(requestReading)).thenReturn(mockResultReading);

        ReadingResponse readingResponse = readingService.addReading(requestReading, BigInteger.valueOf(1));

        Assert.assertNotNull("Result reading response should not be null ", readingResponse);
        Assert.assertNotNull("Result reading should not be null ", readingResponse.getReading());

        ReadingAssertions.execute(mockResultReading,readingResponse.getReading());

        requestReading.setYear(2002);
        requestReading.setMonth(Month.MAY);
        requestReading.setValue(2345L);
        requestReading.setId(readingResponse.getReading().getId());

        Reading mockResultReading2 = new Reading(2002, Month.MAY, 2345L);
        mockResultReading2.setId(readingResponse.getReading().getId());

        Mockito.when(readingRepository.findOne(any())).thenReturn(Optional.of(mockResultReading));
        Mockito.when(readingRepository.save(requestReading)).thenReturn(mockResultReading2);

        readingResponse = readingService.updateReading(requestReading, BigInteger.valueOf(1));

        Assert.assertNotNull("Result reading response should not be null ", readingResponse);
        Assert.assertNotNull("Result reading should not be null ", readingResponse.getReading());

        ReadingAssertions.execute(mockResultReading2,readingResponse.getReading());

    }

    /**
     * Get readinges unit test for {@link ReadingServiceImpl}
     */

    @Test
    public void getReadingsTest() {

        Reading reading1 = new Reading(2001, Month.APRIL, 1234L);
        reading1.setId(BigInteger.valueOf(1));
        Reading reading2 = new Reading(2002, Month.MAY, 1111L);
        reading1.setId(BigInteger.valueOf(2));
        List<Reading> readingList = new ArrayList<>();
        readingList.add(reading1);
        readingList.add(reading2);

        Mockito.when(readingRepository.findAll()).thenReturn(readingList);

        List<Reading> resultReadingList = readingService.getReadings();

        Assert.assertNotNull(resultReadingList);
        Assert.assertEquals(2, resultReadingList.size());


    }

}

