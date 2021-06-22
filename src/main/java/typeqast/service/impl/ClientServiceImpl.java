package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.entities.Client;
import typeqast.repository.ClientRepository;
import typeqast.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public Client addClient(Client client) {

        logger.info("Received add new client request");

        Client saveClient = new Client(client.getName());

        saveClient = clientRepository.save(saveClient);

        logger.info("New client added");

        return saveClient;

    }

    @Override
    public Client updateClient(Client updateClient) {

        logger.info("Received add new client request");

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(updateClient.getId())));

        Client saveClient = null;

        if (resultClient.isPresent()) {

            saveClient = new Client(updateClient.getId(), updateClient.getName());

            saveClient = clientRepository.save(saveClient);

        }

        return saveClient;
    }

    @Override
    public List<Client> getClients() {
        logger.info("Received get clients request");
        return clientRepository.findAll();
    }
}
