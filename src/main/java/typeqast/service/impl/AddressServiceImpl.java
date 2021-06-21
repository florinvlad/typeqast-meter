package typeqast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.response.AddressResponse;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.service.AddressService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public AddressResponse addAddress(Address address, BigInteger clientId) {

        AddressResponse addressResponse = new AddressResponse();

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (resultClient.isPresent()) {

            try {
                address.setClient(resultClient.get());
                addressResponse.setAddress(addressRepository.save(address));
                addressResponse.setStatus(HttpStatus.CREATED);
            } catch (DataIntegrityViolationException dive) {
                addressResponse.setStatus(HttpStatus.BAD_REQUEST);
            }
        } else {
            addressResponse.setStatus(HttpStatus.NOT_FOUND);
        }

        return addressResponse;
    }

    @Override
    public AddressResponse updateAddress(Address address, BigInteger clientId) {

        BigInteger id = address.getId();

        AddressResponse addressResponse = new AddressResponse();

        if (id != null) {
            Optional<Address> readAddress = addressRepository.findOne(Example.of(new Address(id)));
            Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

            if (resultClient.isPresent() && readAddress.isPresent()) {
                address.setClient(resultClient.get());
                addressResponse.setAddress(addressRepository.save(address));
                addressResponse.setStatus(HttpStatus.OK);
            } else {
                addressResponse.setStatus(HttpStatus.NOT_FOUND);
            }
        } else {
            addressResponse.setStatus(HttpStatus.BAD_REQUEST);
        }


        return addressResponse;
    }

    @Override
    public List<Address> getAddresses(BigInteger id) {

        List<Address> addresses = addressRepository.findAll(Example.of(new Address(id)));

        return addresses;
    }

}
