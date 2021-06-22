package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;
import typeqast.service.impl.ClientServiceImpl;
import typeqast.util.assertions.AddressAssertions;
import typeqast.util.assertions.ClientAssertions;
import typeqast.util.assertions.MeterAssertions;
import typeqast.util.assertions.ReadingAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ClientServiceTest {

    @TestConfiguration
    static class ServiceImplTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }

    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private MeterRepository meterRepository;

    @MockBean
    private ReadingRepository readingRepository;

    /**
     * Unit test for {@link ClientServiceImpl#addClient(Client)}
     */
    @Test
    public void addClientTest() {

        Client requestClient = new Client("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        Client resultClient = clientService.addClient(requestClient);

        Assert.assertNotNull(resultClient);

        ClientAssertions.execute(mockClient, resultClient);

    }

    /**
     * Unit test for {@link ClientServiceImpl#updateClient(Client)}
     */
    @Test
    public void updateClientTest() {

        Client requestClient = new Client("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        Client resultClient = clientService.addClient(requestClient);

        Assert.assertNotNull(resultClient);

        ClientAssertions.execute(mockClient, resultClient);

        requestClient.setName("name2");
        requestClient.setId(resultClient.getId());

        mockClient.setName("name2");

        Mockito.when(clientRepository.findOne(any())).thenReturn(Optional.of(mockClient));
        Mockito.when(clientRepository.save(requestClient)).thenReturn(mockClient);

        resultClient = clientService.updateClient(requestClient);

        Assert.assertNotNull("client response should not be null ", resultClient);

        ClientAssertions.execute(mockClient, resultClient);


    }

    /**
     * Unit test for {@link ClientServiceImpl#getClients()}
     */

    @Test
    public void getClientsTest() {

        Client client1 = new Client("name1");
        Client client2 = new Client("name2");
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);

        Mockito.when(clientRepository.findAll()).thenReturn(clientList);

        List<Client> resultClientList = clientService.getClients();

        Assert.assertNotNull(resultClientList);
        Assert.assertEquals(resultClientList.size(), 2);


    }

    /**
     * Test add client with all fields set
     */
    @Test
    public void addClientComprehensiveTest() {

        Client client = new Client("client_name1");
        client.setId(BigInteger.valueOf(1));

        Meter meter = new Meter("meter_name1");
        meter.setId(BigInteger.valueOf(2));

        Address address = new Address("country1", "city1", "street1", 1);
        address.setId(BigInteger.valueOf(3));

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setId(BigInteger.valueOf(4));

        meter.addReading(reading);
        client.setMeter(meter);
        client.setAddress(address);

        Mockito.when(clientRepository.save(any())).thenReturn(client);

        Client resultClient = clientService.addClient(client);

        Assert.assertNotNull(resultClient);

        ClientAssertions.execute(client, resultClient);

        Address resultAddress = resultClient.getAddress();

        AddressAssertions.execute(address, resultAddress);

        Meter resultMeter = resultClient.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        List<Reading> resultReadings = resultMeter.getReadings();

        Assert.assertEquals(false, resultReadings.isEmpty());
        Assert.assertEquals(1, resultReadings.size());

        Reading resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

    }

    /**
     * Test update client with all fields set
     */
    @Test
    public void updateClientComprehensiveTest() {

        Client client = new Client("client_name1");
        client.setId(BigInteger.valueOf(1));

        Meter meter = new Meter("meter_name1");
        meter.setId(BigInteger.valueOf(2));

        Address address = new Address("country1", "city1", "street1", 1);
        address.setId(BigInteger.valueOf(3));

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setId(BigInteger.valueOf(4));

        meter.addReading(reading);
        client.setMeter(meter);
        client.setAddress(address);

        Mockito.when(clientRepository.save(any())).thenReturn(client);

        Client resultClient = clientService.addClient(client);

        Assert.assertNotNull(resultClient);

        ClientAssertions.execute(client, resultClient);

        Address resultAddress = resultClient.getAddress();

        AddressAssertions.execute(address, resultAddress);

        Meter resultMeter = resultClient.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        List<Reading> resultReadings = resultMeter.getReadings();

        Assert.assertEquals(false, resultReadings.isEmpty());
        Assert.assertEquals(1, resultReadings.size());

        Reading resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

        client = new Client("client_name1_updated");
        client.setId(BigInteger.valueOf(1));

        meter = new Meter("meter_name1_updated");
        meter.setId(BigInteger.valueOf(2));

        address = new Address("country1_updated", "city1_updated", "street1_updated", 2);
        address.setId(BigInteger.valueOf(3));

        reading = new Reading(2001, Month.MAY, 2345L);
        reading.setId(BigInteger.valueOf(4));

        meter.addReading(reading);
        client.setMeter(meter);
        client.setAddress(address);

        Mockito.when(clientRepository.findOne(any())).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(any())).thenReturn(client);

        resultClient = clientService.updateClient(client);

        Assert.assertNotNull(resultClient);

        ClientAssertions.execute(client, resultClient);

        resultAddress = resultClient.getAddress();

        AddressAssertions.execute(address, resultAddress);

        resultMeter = resultClient.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        resultReadings = resultMeter.getReadings();

        Assert.assertEquals(false, resultReadings.isEmpty());
        Assert.assertEquals(1, resultReadings.size());

        resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

    }


}
