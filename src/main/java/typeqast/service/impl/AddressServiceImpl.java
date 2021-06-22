package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.service.AddressService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Address addAddress(Address address, BigInteger clientId) {

        logger.info("Received add address request");

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        Address saveAddress = null;

        if (resultClient.isPresent()) {

            saveAddress = new Address(address.getCountry(), address.getCity(), address.getStreet(), address.getNumber());
            saveAddress.setClient(resultClient.get());

            saveAddress = addressRepository.save(saveAddress);

        }

        return saveAddress;
    }

    @Override
    public Address updateAddress(Address address, BigInteger clientId) {

        BigInteger id = address.getId();

        Address saveAddress = null;

        if (id != null) {
            Optional<Address> readAddress = addressRepository.findOne(Example.of(new Address(id)));
            Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

            if (resultClient.isPresent() && readAddress.isPresent()) {

                address.setClient(resultClient.get());
                saveAddress = addressRepository.save(address);

            }
        }

        return saveAddress;
    }

    @Override
    public List<Address> getAddresses(BigInteger id) {

        List<Address> addresses = addressRepository.findAll(Example.of(new Address(id)));

        return addresses;
    }

}
