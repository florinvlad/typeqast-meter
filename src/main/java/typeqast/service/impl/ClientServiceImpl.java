package typeqast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import typeqast.entities.Client;
import typeqast.entities.response.ClientResponse;
import typeqast.repository.ClientRepository;
import typeqast.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientResponse addClient(Client client) {

        ClientResponse clientResponse = new ClientResponse();

        Client saveClient = new Client(client.getName());

        try {
            clientResponse.setClient(clientRepository.save(saveClient));
            clientResponse.setStatus(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            clientResponse.setStatus(HttpStatus.BAD_REQUEST);
        }

        return clientResponse;

    }

    @Override
    public ClientResponse updateClient(Client updateClient) {

        ClientResponse clientResponse = new ClientResponse();

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(updateClient.getId())));

        if (resultClient.isPresent()) {

            try {
                Client saveClient = new Client(updateClient.getId(), updateClient.getName());
                clientResponse.setClient(clientRepository.save(saveClient));
                clientResponse.setStatus(HttpStatus.OK);
            } catch (DataIntegrityViolationException dive) {
                clientResponse.setStatus(HttpStatus.BAD_REQUEST);
            }
        } else {
            clientResponse.setStatus(HttpStatus.NOT_FOUND);
        }

        return clientResponse;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
}
