package typeqast.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Client;
import typeqast.util.assertions.ClientAssertions;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @After
    public void afterTest() {
        clientRepository.deleteAll();
    }

    @Test
    public void addClientTest() {

        Client client = new Client("name1");

        Client resultClient = clientRepository.save(client);

        ClientAssertions.execute(client, resultClient);

    }

    @Test
    public void updateClientTest() {

        String clientName = "name1";

        Client client = new Client(clientName);

        Client resultClient = clientRepository.save(client);

        Assert.assertNotNull(resultClient.getId());
        Assert.assertEquals(clientName, resultClient.getName());

        client = resultClient;
        clientName = "name1_updated";
        client.setName(clientName);

        resultClient = clientRepository.save(client);

        ClientAssertions.execute(client, resultClient);

        Optional<Client> resultClient2 = clientRepository.findOne(Example.of(client));

        Assert.assertTrue(resultClient2.isPresent());

        ClientAssertions.execute(client, resultClient2.get());

    }

    @Test
    public void getClientsTest() {

        Client client = new Client("name1");

        Client resultClient = clientRepository.save(client);

        ClientAssertions.execute(client, resultClient);

        Client client2 = new Client("name2");

        resultClient = clientRepository.save(client2);

        ClientAssertions.execute(client2, resultClient);

        List<Client> clientResultList = clientRepository.findAll();

        Assert.assertNotNull(clientResultList);
        Assert.assertEquals(clientResultList.size(), 2);

    }

}
