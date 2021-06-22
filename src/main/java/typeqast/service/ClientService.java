package typeqast.service;

import typeqast.entities.Client;
import typeqast.entities.response.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse addClient(Client client);

    ClientResponse updateClient(Client client);

    List<Client> getClients();

}
