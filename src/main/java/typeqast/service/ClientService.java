package typeqast.service;

import typeqast.entities.dto.ClientDTO;
import typeqast.entities.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {

    ClientDTO addClient(ClientDTO client);

    ClientDTO updateClient(ClientDTO client) throws ClientNotFoundException;

    List<ClientDTO> getClients();

}
