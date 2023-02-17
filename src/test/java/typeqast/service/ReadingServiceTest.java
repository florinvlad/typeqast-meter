package typeqast.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import typeqast.business.mapper.ReadingMapper;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;
import typeqast.util.assertions.ReadingAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class ReadingServiceTest {

    @InjectMocks
    private ReadingService readingService;

    @Mock
    private ReadingRepository readingRepository;

    @Mock
    private MeterRepository meterRepository;

    @Test
    void addReadingTest() {

        ReadingDTO requestReadingDTO = new ReadingDTO(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Meter(BigInteger.valueOf(1))));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading);

        ReadingDTO readingResponseDTO = readingService.addReading(requestReadingDTO, BigInteger.valueOf(1));

        ReadingAssertions.execute(ReadingMapper.toReadingDTO(mockResultReading), readingResponseDTO);
        assertTrue(true);

    }

    @Test
    void updateReadingTest() {

        ReadingDTO requestReading = new ReadingDTO(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Meter(BigInteger.valueOf(1))));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading);

        ReadingDTO readingResponseDTO = readingService.addReading(requestReading, BigInteger.valueOf(1));

        ReadingAssertions.execute(ReadingMapper.toReadingDTO(mockResultReading), readingResponseDTO);

        requestReading.setYear(2002);
        requestReading.setMonth(Month.MAY);
        requestReading.setValue(2345L);
        requestReading.setId(readingResponseDTO.getId());

        Reading mockResultReading2 = new Reading(2002, Month.MAY, 2345L);
        mockResultReading2.setId(readingResponseDTO.getId());

        Mockito.when(readingRepository.findOne(any())).thenReturn(Optional.of(mockResultReading));
        Mockito.when(readingRepository.save(any())).thenReturn(mockResultReading2);

        readingResponseDTO = readingService.updateReading(requestReading, BigInteger.valueOf(1));

        assertNotNull(readingResponseDTO, "Result reading should not be null ");

        ReadingAssertions.execute(ReadingMapper.toReadingDTO(mockResultReading2), readingResponseDTO);

    }

    @Test
    void getReadingsTest() {

        Reading reading1 = new Reading(2001, Month.APRIL, 1234L);
        reading1.setId(BigInteger.valueOf(1));
        Reading reading2 = new Reading(2002, Month.MAY, 1111L);
        reading1.setId(BigInteger.valueOf(2));
        List<Reading> readingList = new ArrayList<>();
        readingList.add(reading1);
        readingList.add(reading2);

        Mockito.when(readingRepository.findAll()).thenReturn(readingList);

        List<ReadingDTO> resultReadingDTOList = readingService.getReadings();

        assertNotNull(resultReadingDTOList);
        assertEquals(2, resultReadingDTOList.size());

    }

    /**
     * Add reading for inexistent meter
     */
    @Test
    void addReadingInexistentMeterTest() {

        ReadingDTO requestReadingDTO = new ReadingDTO(2001, Month.APRIL, 1234L);

        Reading mockResultReading = new Reading(2001, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        Mockito.when(meterRepository.findOne(any(Example.class))).thenReturn(Optional.empty());

        try {
            readingService.addReading(requestReadingDTO, BigInteger.valueOf(1));
        } catch (MeterNotFoundException mnfe) {
            assertNotNull(mnfe, "Exception not thrown");
        }

    }


}

