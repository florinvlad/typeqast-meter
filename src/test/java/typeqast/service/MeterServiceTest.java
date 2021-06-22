package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.response.MeterResponse;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.service.impl.MeterServiceImpl;
import typeqast.util.assertions.MeterAssertions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class MeterServiceTest {

    @TestConfiguration
    static class ServiceImplTestContextConfiguration {

        @Bean
        public MeterService meterService() {
            return new MeterServiceImpl();
        }

    }

    @Autowired
    private MeterService meterService;

    @MockBean
    private MeterRepository meterRepository;

    @MockBean
    private ClientRepository clientRepository;

    /**
     * Add meter unit test for {@link MeterServiceImpl}
     */
    @Test
    public void addMeterTest() {

        Meter requestMeter = new Meter("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter);

        MeterResponse meterResponse = meterService.addMeter(requestMeter, BigInteger.valueOf(1));

        Assert.assertNotNull("Result meter response should not be null ", meterResponse);
        Assert.assertEquals(HttpStatus.CREATED, meterResponse.getStatus());

        MeterAssertions.execute(mockResultMeter, meterResponse.getMeter());

    }

    /**
     * Update meter unit test for {@link MeterServiceImpl}
     */
    @Test
    public void updateMeterTest() {

        Meter requestMeter = new Meter("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter);

        MeterResponse meterResponse = meterService.addMeter(requestMeter, BigInteger.valueOf(1));

        Assert.assertNotNull("Result meter response should not be null ", meterResponse);

        MeterAssertions.execute(mockResultMeter, meterResponse.getMeter());

        requestMeter.setName("meter1_updated");
        requestMeter.setId(meterResponse.getMeter().getId());

        Meter mockResultMeter2 = new Meter("meter1_updated");
        mockResultMeter2.setId(meterResponse.getMeter().getId());

        Mockito.when(meterRepository.findOne(any())).thenReturn(Optional.of(mockResultMeter));
        Mockito.when(meterRepository.save(any())).thenReturn(mockResultMeter2);

        meterResponse = meterService.updateMeter(requestMeter, BigInteger.valueOf(1));

        Assert.assertNotNull("Result meter response should not be null ", meterResponse);

        MeterAssertions.execute(mockResultMeter2, meterResponse.getMeter());

    }

    /**
     * Get meteres unit test for {@link MeterServiceImpl}
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

        List<Meter> resultMeterList = meterService.getMeters(null);

        Assert.assertNotNull(resultMeterList);
        Assert.assertEquals(2, resultMeterList.size());

    }

    /**
     * Add meter to a client that already has a meter installed
     */
    @Test
    public void addMeterAlreadyAsignedTest() {

        Meter requestMeter = new Meter("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(meterRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        MeterResponse meterResponse = meterService.addMeter(requestMeter, BigInteger.valueOf(1));

        Assert.assertNull("Result meter response should be null ", meterResponse.getMeter());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, meterResponse.getStatus());

    }

    /**
     * Add meter to a client that does not exist
     */
    @Test
    public void addMeterInexistentClientTest() {

        Meter requestMeter = new Meter("meter1");

        Meter mockResultMeter = new Meter("meter1");
        mockResultMeter.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.empty());
        Mockito.when(meterRepository.save(requestMeter)).thenThrow(DataIntegrityViolationException.class);

        MeterResponse meterResponse = meterService.addMeter(requestMeter, BigInteger.valueOf(1));

        Assert.assertNull("Result meter response should be null ", meterResponse.getMeter());
        Assert.assertEquals(HttpStatus.NOT_FOUND, meterResponse.getStatus());

    }

}
