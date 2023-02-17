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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import typeqast.business.mapper.MeterMapperService;
import typeqast.business.mapper.impl.MeterMapperServiceImpl;
import typeqast.business.processor.ReadingProcessor;
import typeqast.business.processor.impl.AggregateReadingCalculator;
import typeqast.entities.AggregateReading;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.MeterDTO;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.service.impl.MeterServiceImpl;
import typeqast.util.assertions.MeterAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SuppressWarnings("unchecked")
public class MeterServiceTest {

    @TestConfiguration
    static class ServiceImplTestContextConfiguration {

        @Bean
        public MeterService meterService() {
            return new MeterServiceImpl();
        }

    }

    @TestConfiguration
    static class MapperServiceImplTestContextConfiguration {

        @Bean
        public MeterMapperService meterMapperService() {
            return new MeterMapperServiceImpl();
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
    @Qualifier("aggregateReading")
    private ReadingProcessor readingProcessor;

    @Autowired
    private MeterMapperService meterMapperService;

    @Autowired
    private MeterService meterService;

    @MockBean
    private MeterRepository meterRepository;

    @MockBean
    private ClientRepository clientRepository;

    /**
     * Add meter unit test for {@link MeterServiceImpl#addMeter(MeterDTO, BigInteger)}
     */
    @Test
    public void addMeterTest() {

        MeterDTO requestMeterDTO = new MeterDTO("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter);

        MeterDTO meterResponseDTO = meterService.addMeter(requestMeterDTO, BigInteger.valueOf(1));

        Assert.assertNotNull("Result meter response should not be null ", meterResponseDTO);

        MeterAssertions.execute(meterMapperService.toMeterDTO(mockResultMeter), meterResponseDTO);

    }

    /**
     * Update meter unit test for {@link MeterServiceImpl#updateMeter(MeterDTO, BigInteger)}
     */
    @Test
    public void updateMeterTest() {

        MeterDTO requestMeterDTO = new MeterDTO("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter);

        MeterDTO meterResponseDTO = meterService.addMeter(requestMeterDTO, BigInteger.valueOf(1));

        Assert.assertNotNull("Result meter response should not be null ", meterResponseDTO);

        MeterAssertions.execute(meterMapperService.toMeterDTO(mockResultMeter), meterResponseDTO);

        requestMeterDTO.setName("meter1_updated");
        requestMeterDTO.setId(meterResponseDTO.getId());

        Meter mockResultMeter2 = new Meter("meter1_updated");
        mockResultMeter2.setId(meterResponseDTO.getId());

        Mockito.when(meterRepository.findOne(any())).thenReturn(Optional.of(mockResultMeter));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter2);

        meterResponseDTO = meterService.updateMeter(requestMeterDTO, BigInteger.valueOf(1));

        Assert.assertNotNull("Result meter response should not be null ", meterResponseDTO);

        MeterAssertions.execute(meterMapperService.toMeterDTO(mockResultMeter2), meterResponseDTO);

    }

    /**
     * Get meteres unit test for {@link MeterServiceImpl#getMeters(BigInteger)}
     */
    @Test
    public void getMetersTest() {

        Meter meter1 = new Meter("meter1");
        meter1.setId(BigInteger.valueOf(1));
        Meter meter2 = new Meter("meter2");
        meter1.setId(BigInteger.valueOf(2));
        List<Meter> meterList = new ArrayList<>();
        meterList.add(meter1);
        meterList.add(meter2);

        Mockito.when(meterRepository.findAll(any(Example.class))).thenReturn(meterList);

        List<MeterDTO> resultMeterList = meterService.getMeters(null);

        Assert.assertNotNull(resultMeterList);
        Assert.assertEquals(2, resultMeterList.size());

    }

    /**
     * Add meter to a client that does not exist
     */
    @Test
    public void addMeterInexistentClientTest() {

        MeterDTO requestMeterDTO = new MeterDTO("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.empty());
        Mockito.when(meterRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        try {
            meterService.addMeter(requestMeterDTO, BigInteger.valueOf(1));
        } catch (ClientNotFoundException cnfe) {
            Assert.assertNotNull("Client exception should be thrown ", cnfe);

        }


    }

    /**
     * Get aggregate readings unit test for {@link MeterServiceImpl#getAggregateReadings(Integer, BigInteger)}
     */
    @Test
    public void getAggregateReadingsTest() {

        Meter mockMeter = new Meter("meter1");
        mockMeter.setId(BigInteger.valueOf(1));
        Reading reading = new Reading(2000, Month.JANUARY, 1111L);
        mockMeter.addReading(reading);
        reading = new Reading(2000, Month.FEBRUARY, 1111L);
        mockMeter.addReading(reading);
        reading = new Reading(2000, Month.MARCH, 1111L);
        mockMeter.addReading(reading);

        Mockito.when(meterRepository.findOne(any())).thenReturn(Optional.of(mockMeter));

        AggregateReading aggregateReading = meterService.getAggregateReadings(2000, mockMeter.getId());

        Assert.assertNotNull(aggregateReading);
        Assert.assertEquals(reading.getYear(), aggregateReading.getYear());
        Long mockTotal = 3333L;
        Assert.assertEquals(mockTotal, aggregateReading.getTotal());


    }

}
