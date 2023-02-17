package typeqast.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import typeqast.business.mapper.ClientMapper;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.ClientDTO;
import typeqast.repository.ClientRepository;
import typeqast.util.assertions.AddressAssertions;
import typeqast.util.assertions.ClientAssertions;
import typeqast.util.assertions.MeterAssertions;
import typeqast.util.assertions.ReadingAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    /**
     * Unit test for {@link ClientService#addClient(ClientDTO)}
     */

    public void addClientTest() {

        ClientDTO requestClientDTO = new ClientDTO("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        ClientDTO resultClientDTO = clientService.addClient(requestClientDTO);

        assertNotNull(resultClientDTO);

        ClientAssertions.execute(ClientMapper.toClientDTO(mockClient), resultClientDTO);

    }

    /**
     * Unit test for {@link ClientService#updateClient(ClientDTO)}
     */
    @Test
    void updateClientTest() {

        ClientDTO requestClientDTO = new ClientDTO("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        ClientDTO resultClientDTO = clientService.addClient(requestClientDTO);

        assertNotNull(resultClientDTO);

        ClientAssertions.execute(ClientMapper.toClientDTO(mockClient), resultClientDTO);

        requestClientDTO.setName("name2");
        requestClientDTO.setId(resultClientDTO.getId());

        mockClient.setName("name2");

        Mockito.when(clientRepository.findOne(any())).thenReturn(Optional.of(mockClient));
        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        resultClientDTO = clientService.updateClient(requestClientDTO);

        assertNotNull(resultClientDTO, "client response should not be null ");

        ClientAssertions.execute(ClientMapper.toClientDTO(mockClient), resultClientDTO);


    }

    /**
     * Unit test for {@link ClientService#getClients()}
     */

    @Test
    void getClientsTest() {

        Client client1 = new Client("name1");
        Client client2 = new Client("name2");
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);

        Mockito.when(clientRepository.findAll()).thenReturn(clientList);

        List<ClientDTO> resultClientList = clientService.getClients();

        assertNotNull(resultClientList);
        assertEquals(2, resultClientList.size());


    }

    /**
     * Test add client with all fields set
     */
    @Test
    void addClientComprehensiveTest() {

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

        assertNotNull(resultClientDTO);

        ClientAssertions.execute(ClientMapper.toClientDTO(mockClient), resultClientDTO);

        Address resultAddress = resultClientDTO.getAddress();

        AddressAssertions.execute(address, resultAddress);

        Meter resultMeter = resultClientDTO.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        List<Reading> resultReadings = resultMeter.getReadings();

        assertFalse(resultReadings.isEmpty());
        assertEquals(1, resultReadings.size());

        Reading resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

    }

    /**
     * Test update client with all fields set
     */
    @Test
    void updateClientComprehensiveTest() {

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

        assertNotNull(resultClientDTO);

        ClientAssertions.execute(ClientMapper.toClientDTO(mockClient), resultClientDTO);

        Address resultAddress = resultClientDTO.getAddress();

        AddressAssertions.execute(address, resultAddress);

        Meter resultMeter = resultClientDTO.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        List<Reading> resultReadings = resultMeter.getReadings();

        assertFalse(resultReadings.isEmpty());
        assertEquals(1, resultReadings.size());

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

        assertNotNull(resultClientDTO);

        ClientAssertions.execute(ClientMapper.toClientDTO(mockClient), resultClientDTO);

        resultAddress = resultClientDTO.getAddress();

        AddressAssertions.execute(address, resultAddress);

        resultMeter = resultClientDTO.getMeter();

        MeterAssertions.execute(meter, resultMeter);

        resultReadings = resultMeter.getReadings();

        assertFalse(resultReadings.isEmpty());
        assertEquals(1, resultReadings.size());

        resultReading = resultReadings.get(0);

        ReadingAssertions.execute(reading, resultReading);

    }


}
