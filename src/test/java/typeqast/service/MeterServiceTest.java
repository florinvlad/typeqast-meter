package typeqast.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import typeqast.business.mapper.MeterMapper;
import typeqast.business.processor.ReadingProcessor;
import typeqast.entities.AggregateReading;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.MeterDTO;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.util.assertions.MeterAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class MeterServiceTest {


    @Mock
    @Qualifier("aggregateReading")
    private ReadingProcessor readingProcessor;

    @InjectMocks
    private MeterService meterService;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void addMeterTest() {

        MeterDTO requestMeterDTO = new MeterDTO("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter);

        MeterDTO meterResponseDTO = meterService.addMeter(requestMeterDTO, BigInteger.valueOf(1));

        Assertions.assertNotNull(meterResponseDTO, "Result meter response should not be null ");

        MeterAssertions.execute(MeterMapper.toMeterDTO(mockResultMeter), meterResponseDTO);

    }

    @Test
    void updateMeterTest() {

        MeterDTO requestMeterDTO = new MeterDTO("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter);

        MeterDTO meterResponseDTO = meterService.addMeter(requestMeterDTO, BigInteger.valueOf(1));

        Assertions.assertNotNull(meterResponseDTO, "Result meter response should not be null ");

        MeterAssertions.execute(MeterMapper.toMeterDTO(mockResultMeter), meterResponseDTO);

        requestMeterDTO.setName("meter1_updated");
        requestMeterDTO.setId(meterResponseDTO.getId());

        Meter mockResultMeter2 = new Meter("meter1_updated");
        mockResultMeter2.setId(meterResponseDTO.getId());

        Mockito.when(meterRepository.findOne(any())).thenReturn(Optional.of(mockResultMeter));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter2);

        meterResponseDTO = meterService.updateMeter(requestMeterDTO, BigInteger.valueOf(1));

        Assertions.assertNotNull(meterResponseDTO, "Result meter response should not be null ");

        MeterAssertions.execute(MeterMapper.toMeterDTO(mockResultMeter2), meterResponseDTO);

    }

    @Test
    void getMetersTest() {

        Meter meter1 = new Meter("meter1");
        meter1.setId(BigInteger.valueOf(1));
        Meter meter2 = new Meter("meter2");
        meter1.setId(BigInteger.valueOf(2));
        List<Meter> meterList = new ArrayList<>();
        meterList.add(meter1);
        meterList.add(meter2);

        Mockito.when(meterRepository.findAll(any(Example.class))).thenReturn(meterList);

        List<MeterDTO> resultMeterList = meterService.getMeters(null);

        Assertions.assertNotNull(resultMeterList);
        Assertions.assertEquals(2, resultMeterList.size());

    }

    /**
     * Add meter to a client that does not exist
     */
    @Test
    void addMeterInexistentClientTest() {

        MeterDTO requestMeterDTO = new MeterDTO("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.empty());

        try {
            meterService.addMeter(requestMeterDTO, BigInteger.valueOf(1));
        } catch (ClientNotFoundException cnfe) {
            Assertions.assertNotNull(cnfe, "Client exception should be thrown ");

        }


    }

    @Test
    void getAggregateReadingsTest() {

        Meter mockMeter = new Meter("meter1");
        mockMeter.setId(BigInteger.valueOf(1));
        Reading reading = new Reading(2000, Month.JANUARY, 1111L);
        mockMeter.addReading(reading);
        reading = new Reading(2000, Month.FEBRUARY, 1111L);
        mockMeter.addReading(reading);
        reading = new Reading(2000, Month.MARCH, 1111L);
        mockMeter.addReading(reading);

        Mockito.when(meterRepository.findOne(any())).thenReturn(Optional.of(mockMeter));
        Mockito.when(readingProcessor.process(any())).thenReturn(3333l);

        AggregateReading aggregateReading = meterService.getAggregateReadings(2000, mockMeter.getId());

        Assertions.assertNotNull(aggregateReading);
        Assertions.assertEquals(reading.getYear(), aggregateReading.getYear());
        Long mockTotal = 3333L;
        Assertions.assertEquals(mockTotal, aggregateReading.getTotal());


    }

}
