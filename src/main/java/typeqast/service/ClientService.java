package typeqast.service;

import typeqast.entities.Client;

import java.util.List;

public interface ClientService {

    Client addClient(Client client);

    Client updateClient(Client client);

    List<Client> getClients();

}
