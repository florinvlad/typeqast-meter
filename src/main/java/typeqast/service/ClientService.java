package typeqast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.mapper.ClientMapper;
import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link typeqast.service.ClientService}
 */
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private static Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ClientDTO addClient(ClientDTO clientDTO) {

        logger.info("Received add new client request");

        Client saveClient = new Client(clientDTO.getName());

        saveClient = clientRepository.save(saveClient);

        logger.info("New client added");

        return ClientMapper.toClientDTO(saveClient);

    }

    public ClientDTO updateClient(ClientDTO updateClientDTO) throws ClientNotFoundException {

        logger.info("Received add new client request");

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(updateClientDTO.getId())));

        Client saveClient;

        if (!resultClient.isPresent()) throw new ClientNotFoundException();

        saveClient = new Client(updateClientDTO.getId(), updateClientDTO.getName());

        saveClient = clientRepository.save(saveClient);

        return ClientMapper.toClientDTO(saveClient);
    }

    public List<ClientDTO> getClients() {
        logger.info("Received get clients request");


        List<Client> resultClientList = clientRepository.findAll();
        List<ClientDTO> resultClientDTOList = new ArrayList<>();
        for (Client client : resultClientList) {
            resultClientDTOList.add(ClientMapper.toClientDTO(client));
        }

        return resultClientDTOList;
    }
}
