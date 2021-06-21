package typeqast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import typeqast.constants.RestEndpoints;
import typeqast.entities.Client;
import typeqast.entities.response.ClientResponse;
import typeqast.service.ClientService;

import javax.validation.ConstraintViolationException;
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
     *
     * @return
     */
    @PostMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<Client> addClient(@RequestBody Client client) {

        logger.info("Received create client request");

        ClientResponse clientResponse = clientService.addClient(client);

        return new ResponseEntity<>(clientResponse.getClient(), clientResponse.getStatus());

    }

    /**
     * Update existing client
     *
     * @param client json body
     *
     * @return
     */
    @PutMapping(RestEndpoints.CLIENTS)
    public ResponseEntity<Object> updateClient(@RequestBody Client client) {

        logger.info("Received update client request");

        ClientResponse clientResponse = clientService.updateClient(client);

        return new ResponseEntity<>(clientResponse.getClient(), clientResponse.getStatus());

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> onValidationError(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
