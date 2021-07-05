package typeqast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import typeqast.constants.RestEndpoints;
import typeqast.entities.dto.ClientDTO;
import typeqast.service.ClientService;

import java.util.List;

@RestController
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    /**
     * Add new client
     *
     * @param clientDTO request body as json
     * @return
     */
    @PostMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {

        logger.info("Received create client request");

        ClientDTO resultClientDTO = clientService.addClient(clientDTO);

        return new ResponseEntity<>(resultClientDTO, HttpStatus.CREATED);
    }

    /**
     * Update existing client
     *
     * @param clientDTO json body
     * @return
     */
    @PutMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO) {

        logger.info("Received update client request");

        ClientDTO resultClient = clientService.updateClient(clientDTO);

        return new ResponseEntity<>(resultClient, HttpStatus.OK);

    }

    /**
     * Get a list of all clients
     *
     * @return
     */
    @GetMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<List<ClientDTO>> getClients() {
        logger.info("Received get client request");

        List<ClientDTO> clientList = clientService.getClients();

        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

}
