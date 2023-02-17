package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import typeqast.business.mapper.ReadingMapperService;
import typeqast.business.mapper.impl.ReadingMapperServiceImpl;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;
import typeqast.entities.exception.MeterNotFoundException;
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
@SuppressWarnings("unchecked")
public class ReadingServiceTest {

    @TestConfiguration
    static class ServiceImplTestContextConfiguration {

        @Bean
        public ReadingService readingService() {
            return new ReadingServiceImpl();
        }

    }

    @TestConfiguration
    static class MapperServiceImplTestContextConfiguration {

        @Bean
        public ReadingMapperService readingMapperService() {
            return new ReadingMapperServiceImpl();
        }

    }

    @Autowired
    private ReadingMapperService readingMapperService;

    @Autowired
    private ReadingService readingService;

    @MockBean
    private ReadingRepository readingRepository;

    @MockBean
    private MeterRepository meterRepository;

    /**
     * Add reading unit test for {@link ReadingServiceImpl#addReading(ReadingDTO, BigInteger)}
     */
    @Test
    public void addReadingTest() {

        ReadingDTO requestReadingDTO = new ReadingDTO(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Meter(BigInteger.valueOf(1))));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading);

        ReadingDTO readingResponseDTO = readingService.addReading(requestReadingDTO, BigInteger.valueOf(1));

        ReadingAssertions.execute(readingMapperService.toReadingDTO(mockResultReading), readingResponseDTO);

    }

    /**
     * Update reading unit test for {@link ReadingServiceImpl#updateReading(ReadingDTO, BigInteger)}
     */
    @Test
    public void updateReadingTest() {

        ReadingDTO requestReading = new ReadingDTO(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Meter(BigInteger.valueOf(1))));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading);

        ReadingDTO readingResponseDTO = readingService.addReading(requestReading, BigInteger.valueOf(1));

        ReadingAssertions.execute(readingMapperService.toReadingDTO(mockResultReading), readingResponseDTO);

        requestReading.setYear(2002);
        requestReading.setMonth(Month.MAY);
        requestReading.setValue(2345L);
        requestReading.setId(readingResponseDTO.getId());

        Reading mockResultReading2 = new Reading(2002, Month.MAY, 2345L);
        mockResultReading2.setId(readingResponseDTO.getId());

        Mockito.when(readingRepository.findOne(any())).thenReturn(Optional.of(mockResultReading));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading2);

        readingResponseDTO = readingService.updateReading(requestReading, BigInteger.valueOf(1));

        Assert.assertNotNull("Result reading should not be null ", readingResponseDTO);

        ReadingAssertions.execute(readingMapperService.toReadingDTO(mockResultReading2), readingResponseDTO);

    }

    /**
     * Get readinges unit test for {@link ReadingServiceImpl#getReadings()}
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

        List<ReadingDTO> resultReadingDTOList = readingService.getReadings();

        Assert.assertNotNull(resultReadingDTOList);
        Assert.assertEquals(2, resultReadingDTOList.size());

    }

    /**
     * Add reading for inexistent meter
     */
    @Test
    public void addReadingInexistentMeterTest() {

        ReadingDTO requestReadingDTO = new ReadingDTO(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.empty());
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading);

        try {
            readingService.addReading(requestReadingDTO, BigInteger.valueOf(1));
        } catch (MeterNotFoundException mnfe) {
            Assert.assertNotNull("Exception not thrown", mnfe);
        }

    }


}

