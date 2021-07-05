package typeqast.business.transformer;

import org.springframework.stereotype.Service;
import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;

@Service
public interface ClientMapperService {

    Client toClient(ClientDTO clientDTO);

    ClientDTO toClientDTO(Client client);

}
