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

        Assert.assertNotNull(resultClient.getId());
        Assert.assertEquals(client.getName(), resultClient.getName());

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
        Assert.assertNotNull(resultClient.getId());
        Assert.assertEquals(clientName, resultClient.getName());


        Optional<Client> resultClient2 = clientRepository.findOne(Example.of(client));

        Assert.assertTrue(resultClient2.isPresent());

        Assert.assertEquals(clientName, resultClient2.get().getName());

    }

    @Test
    public void getClientsTest() {

        Client client = new Client("name1");

        Client resultClient = clientRepository.save(client);

        Assert.assertNotNull(resultClient);
        Assert.assertNotNull(resultClient.getId());
        Assert.assertEquals(client.getName(), resultClient.getName());

        Client client2 = new Client("name2");

        resultClient = clientRepository.save(client2);

        Assert.assertNotNull(resultClient);
        Assert.assertNotNull(resultClient.getId());
        Assert.assertEquals(client2.getName(), resultClient.getName());

        List<Client> clientResultList = clientRepository.findAll();

        Assert.assertNotNull(clientResultList);
        Assert.assertEquals(clientResultList.size(), 2);

    }

}
