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

import typeqast.business.mapper.ClientMapperService;
import typeqast.business.mapper.impl.ClientMapperServiceImpl;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.ClientDTO;
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

    @TestConfiguration
    static class MapperServiceImplTestContextConfiguration {

        @Bean
        public ClientMapperService clientMapperService() {
            return new ClientMapperServiceImpl();
        }

    }

    @Autowired
    private ClientMapperService clientMapperService;

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
     * Unit test for {@link ClientServiceImpl#addClient(ClientDTO)}
     */
    @Test
    public void addClientTest() {

        ClientDTO requestClientDTO = new ClientDTO("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        ClientDTO resultClientDTO = clientService.addClient(requestClientDTO);

        Assert.assertNotNull(resultClientDTO);

        ClientAssertions.execute(clientMapperService.toClientDTO(mockClient), resultClientDTO);

    }

    /**
     * Unit test for {@link ClientServiceImpl#updateClient(ClientDTO)}
     */
    @Test
    public void updateClientTest() {

        ClientDTO requestClientDTO = new ClientDTO("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        ClientDTO resultClientDTO = clientService.addClient(requestClientDTO);

        Assert.assertNotNull(resultClientDTO);

        ClientAssertions.execute(clientMapperService.toClientDTO(mockClient), resultClientDTO);

        requestClientDTO.setName("name2");
        requestClientDTO.setId(resultClientDTO.getId());

        mockClient.setName("name2");

        Mockito.when(clientRepository.findOne(any())).thenReturn(Optional.of(mockClient));
        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        resultClientDTO = clientService.updateClient(requestClientDTO);

        Assert.assertNotNull("client response should not be null ", resultClientDTO);

        ClientAssertions.execute(clientMapperService.toClientDTO(mockClient), resultClientDTO);


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

        List<ClientDTO> resultClientList = clientService.getClients();

        Assert.assertNotNull(resultClientList);
        Assert.assertEquals(resultClientList.size(), 2);


    }

    /**
     * Test add client with all fields set
     */
    @Test
    public void addClientComprehensiveTest() {

        ClientDTO requestClientDTO = new ClientDTO("name1");

        Client mockClient = new Client("client_name1");
        mockClient.setId(BigInteger.valueOf(1));

        Meter meter = new Meter("meter_name1");
        meter.setId(BigInteger.valueOf(2));

        Address address = new Address("country1", "city1", "street1", 1);
        address.setId(BigInteger.valueOf(3));

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setId(BigInteger.valueOf(4));

        meter.addReading(reading);
        mockClient.setMeter(meter);
        mockClient.setAddress(address);

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        ClientDTO resultClientDTO = clientService.addClient(requestClientDTO);

        Assert.assertNotNull(resultClientDTO);

        ClientAssertions.execute(clientMapperService.toClientDTO(mockClient), resultClientDTO);

        Address resultAddress = resultClientDTO.getAddress();

        AddressAssertions.execute(address, resultAddress);

        Meter resultMeter = resultClientDTO.getMeter();

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

        ClientDTO requestClientDTO = new ClientDTO("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Meter meter = new Meter("meter_name1");
        meter.setId(BigInteger.valueOf(2));

        Address address = new Address("country1", "city1", "street1", 1);
        address.setId(BigInteger.valueOf(3));

        Reading reading = new Reading(2000, Month.APRIL, 1234L);
        reading.setId(BigInteger.valueOf(4));

        meter.addReading(reading);

        mockClient.setMeter(meter);
        mockClient.setAddress(address);

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        ClientDTO resultClientDTO = clientService.addClient(requestClientDTO);

        Assert.assertNotNull(resultClientDTO);

        ClientAssertions.execute(clientMapperService.toClientDTO(mockClient), resultClientDTO);

        Address resultAddress = resultClientDTO.getAddress();

        AddressAssertions.execute(address, resultAddress);

        Meter resultMeter = resultClientDTO.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        List<Reading> resultReadings = resultMeter.getReadings();

        Assert.assertEquals(false, resultReadings.isEmpty());
        Assert.assertEquals(1, resultReadings.size());

        Reading resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

        mockClient.setName("client_name1_updated");

        meter = new Meter("meter_name1_updated");
        meter.setId(BigInteger.valueOf(2));

        address = new Address("country1_updated", "city1_updated", "street1_updated", 2);
        address.setId(BigInteger.valueOf(3));

        reading = new Reading(2001, Month.MAY, 2345L);
        reading.setId(BigInteger.valueOf(4));

        meter.addReading(reading);
        mockClient.setMeter(meter);
        mockClient.setAddress(address);

        Mockito.when(clientRepository.findOne(any())).thenReturn(Optional.of(mockClient));
        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        resultClientDTO = clientService.updateClient(requestClientDTO);

        Assert.assertNotNull(resultClientDTO);

        ClientAssertions.execute(clientMapperService.toClientDTO(mockClient), resultClientDTO);

        resultAddress = resultClientDTO.getAddress();

        AddressAssertions.execute(address, resultAddress);

        resultMeter = resultClientDTO.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        resultReadings = resultMeter.getReadings();

        Assert.assertEquals(false, resultReadings.isEmpty());
        Assert.assertEquals(1, resultReadings.size());

        resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

    }


}
