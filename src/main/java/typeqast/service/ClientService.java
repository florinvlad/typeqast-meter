package typeqast.service;

import typeqast.entities.Client;
import typeqast.entities.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {

    Client addClient(Client client);

    Client updateClient(Client client) throws ClientNotFoundException;

    List<Client> getClients();

}
