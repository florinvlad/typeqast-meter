package typeqast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import typeqast.constants.RequestParams;
import typeqast.constants.RestEndpoints;
import typeqast.entities.Address;
import typeqast.entities.response.AddressResponse;
import typeqast.service.AddressService;

import javax.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.util.List;

@RestController
public class AddressController {

    private static Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    /**
     * Add new address
     *
     * @param address  json body
     * @param clientId client id for address
     * @return
     */
    @PostMapping(RestEndpoints.ADDRESSES)
    public ResponseEntity<Address> addAddress(@RequestBody Address address, @RequestParam(name = RequestParams.CLIENT_ID) BigInteger clientId) {

        logger.info("Received create addresses request");

        AddressResponse addressResponse = addressService.addAddress(address, clientId);

        return new ResponseEntity<>(addressResponse.getAddress(), addressResponse.getStatus());

    }

    /**
     * Update address for an existing client
     *
     * @param address json body
     * @param clientId id of client
     *
     * @return
     */
    @PutMapping(RestEndpoints.ADDRESSES)
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, BigInteger clientId) {

        logger.info("Received update addresses request");

        AddressResponse responseAddress = addressService.updateAddress(address, clientId);

        return new ResponseEntity<>(responseAddress.getAddress(), responseAddress.getStatus());

    }

    /**
     * Get list of addresses
     *
     * @param id - optional address id
     * @return
     */
    @GetMapping(RestEndpoints.ADDRESSES)
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = RequestParams.ID, required = false) BigInteger id) {
        logger.info("Received get address request");

        List<Address> addresses = addressService.getAddresses(id);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> onValidationError(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
