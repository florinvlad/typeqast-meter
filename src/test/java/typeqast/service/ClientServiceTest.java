package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.response.ClientResponse;
import typeqast.repository.ClientRepository;
import typeqast.service.impl.ClientServiceImpl;

import java.math.BigInteger;
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

    /**
     * Add client unit test for {@link ClientServiceImpl}
     */
    @Test
    public void addClientTest() {

        Client requestClient = new Client("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(requestClient)).thenReturn(mockClient);

        ClientResponse clientResponse = clientService.addClient(requestClient);

        Assert.assertNotNull("Client should not be null ", clientResponse.getClient());
        Assert.assertNotNull("Id should not be null ", clientResponse.getClient().getId());
        Assert.assertEquals(requestClient.getName(), clientResponse.getClient().getName());

    }

    /**
     * Update client unit test for {@link ClientServiceImpl}
     */
    @Test
    public void updateClientTest() {

        Client requestClient = new Client("name1");

        Client mockClient = new Client("name1");
        mockClient.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.save(requestClient)).thenReturn(mockClient);

        ClientResponse clientResponse = clientService.addClient(requestClient);

        Assert.assertNotNull("Client should not be null ", clientResponse.getClient());
        Assert.assertNotNull("Id should not be null ", clientResponse.getClient().getId());
        Assert.assertEquals(requestClient.getName(), clientResponse.getClient().getName());

        requestClient.setName("name2");
        requestClient.setId(clientResponse.getClient().getId());

        mockClient.setName("name2");

        Mockito.when(clientRepository.findOne(any())).thenReturn(Optional.of(mockClient));
        Mockito.when(clientRepository.save(requestClient)).thenReturn(mockClient);

        clientResponse = clientService.updateClient(requestClient);

        Assert.assertNotNull("result client should not be null ", clientResponse);
        Assert.assertNotNull("result client should not be null ", clientResponse.getClient());
        Assert.assertNotNull("Id should not be null ", clientResponse.getClient().getId());
        Assert.assertEquals(requestClient.getName(), clientResponse.getClient().getName());


    }

    /**
     * Get clients unit test for {@link ClientServiceImpl}
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

}
