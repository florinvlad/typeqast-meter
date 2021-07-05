package typeqast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import typeqast.constants.RestEndpoints;
import typeqast.entities.Client;
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
     * @param client request body as json
     * @return
     */
    @PostMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<Client> addClient(@RequestBody Client client) {

        logger.info("Received create client request");

        Client resultClient = clientService.addClient(client);

        return new ResponseEntity<>(resultClient, HttpStatus.CREATED);
    }

    /**
     * Update existing client
     *
     * @param client json body
     * @return
     */
    @PutMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<Object> updateClient(@RequestBody Client client) {

        logger.info("Received update client request");

        Client resultClient = clientService.updateClient(client);

        return new ResponseEntity<>(resultClient, HttpStatus.OK);

    }

    /**
     * Get a list of all clients
     *
     * @return
     */
    @GetMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<List<Client>> getClients() {
        logger.info("Received get client request");

        List<Client> clientList = clientService.getClients();

        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

}
