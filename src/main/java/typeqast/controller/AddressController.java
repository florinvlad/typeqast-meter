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
import typeqast.service.AddressService;

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

        Address resultAddress = addressService.addAddress(address, clientId);

        return new ResponseEntity<>(resultAddress, HttpStatus.CREATED);

    }

    /**
     * Update address for an existing client
     *
     * @param address   json body
     * @param clientId  id of client
     * @param addressId id of address
     * @return
     */
    @PutMapping(RestEndpoints.ADDRESSES)
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @RequestParam(name = RequestParams.CLIENT_ID) BigInteger clientId, @RequestParam(name = RequestParams.ADDRESS_ID) BigInteger addressId) {

        logger.info("Received update addresses request");

        address.setId(addressId);

        Address resultAddress = addressService.updateAddress(address, clientId);

        return new ResponseEntity<>(resultAddress, HttpStatus.OK);

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

}
