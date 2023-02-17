package typeqast.business.mapper;

import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;

public class ClientMapper {
    private ClientMapper() {
    }

    public static Client toClient(ClientDTO clientDTO) {

        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setId(clientDTO.getId());
        client.setMeter(clientDTO.getMeter());
        client.setAddress(clientDTO.getAddress());

        return client;
    }

    public static ClientDTO toClientDTO(Client client) {

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setId(client.getId());
        clientDTO.setMeter(client.getMeter());
        clientDTO.setAddress(client.getAddress());

        return clientDTO;
    }
}
