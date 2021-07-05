package typeqast.business.transformer.impl;

import org.springframework.stereotype.Service;
import typeqast.business.transformer.ClientMapperService;
import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;

@Service
public class ClientMapperServiceImpl implements ClientMapperService {

    @Override
    public Client toClient(ClientDTO clientDTO) {

        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setId(clientDTO.getId());
        client.setMeter(clientDTO.getMeter());
        client.setAddress(clientDTO.getAddress());

        return client;
    }

    @Override
    public ClientDTO toClientDTO(Client client) {

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setId(client.getId());
        clientDTO.setMeter(client.getMeter());
        clientDTO.setAddress(client.getAddress());

        return clientDTO;
    }

}
