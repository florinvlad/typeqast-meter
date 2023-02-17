package typeqast.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import typeqast.entities.Client;
import typeqast.util.assertions.ClientAssertions;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @AfterEach
    public void afterEachTest() {
        clientRepository.deleteAll();
    }

    @Test
    void addClientTest() {

        Client client = new Client("name1");

        Client resultClient = clientRepository.save(client);

        ClientAssertions.execute(client, resultClient);
        Assertions.assertTrue(true);

    }

    @Test
    void updateClientTest() {

        String clientName = "name1";

        Client client = new Client(clientName);

        Client resultClient = clientRepository.save(client);

        Assertions.assertNotNull(resultClient.getId());
        Assertions.assertEquals(clientName, resultClient.getName());

        client = resultClient;
        clientName = "name1_updated";
        client.setName(clientName);

        resultClient = clientRepository.save(client);

        ClientAssertions.execute(client, resultClient);

        Optional<Client> resultClient2 = clientRepository.findOne(Example.of(client));

        Assertions.assertTrue(resultClient2.isPresent());

        ClientAssertions.execute(client, resultClient2.get());

    }

    @Test
    void getClientsTest() {

        Client client = new Client("name1");

        Client resultClient = clientRepository.save(client);

        ClientAssertions.execute(client, resultClient);

        Client client2 = new Client("name2");

        resultClient = clientRepository.save(client2);

        ClientAssertions.execute(client2, resultClient);

        List<Client> clientResultList = clientRepository.findAll();

        Assertions.assertNotNull(clientResultList);
        Assertions.assertEquals(2,clientResultList.size());

    }

}
