package typeqast.service;

import org.springframework.stereotype.Service;
import typeqast.entities.Client;
import typeqast.entities.response.ClientResponse;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public interface ClientService {

    ClientResponse addClient(Client client);

    ClientResponse updateClient(Client client);

    List<Client> getClients();

}
