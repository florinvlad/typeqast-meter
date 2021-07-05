package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.exception.AddressNotFoundException;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.service.AddressService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link AddressService}
 */
@Service
public class AddressServiceImpl implements AddressService {

    private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Implementation for {@link AddressService#addAddress(Address, BigInteger)}
     *
     * @param address
     * @param clientId
     * @return
     */
    @Override
    public Address addAddress(Address address, BigInteger clientId) throws ClientNotFoundException {

        logger.info("Received add address request");

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        Address saveAddress;

        if (!resultClient.isPresent()) throw new ClientNotFoundException();

        saveAddress = new Address(address.getCountry(), address.getCity(), address.getStreet(), address.getNumber());
        saveAddress.setClient(resultClient.get());

        saveAddress = addressRepository.save(saveAddress);

        return saveAddress;
    }

    /**
     * Implementation for {@link AddressService#updateAddress(Address, BigInteger)}
     *
     * @param address
     * @param clientId
     * @return
     */
    @Override
    public Address updateAddress(Address address, BigInteger clientId) throws ClientNotFoundException, AddressNotFoundException {

        BigInteger addressId = address.getId();

        Address saveAddress;

        Optional<Address> resultAddress = addressRepository.findOne(Example.of(new Address(addressId)));
        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) throw new ClientNotFoundException();
        if (!resultAddress.isPresent()) throw new AddressNotFoundException();

        address.setClient(resultClient.get());
        saveAddress = addressRepository.save(address);


        return saveAddress;
    }

    /**
     * Implementation for {@link AddressService#getAddresses(BigInteger)}
     *
     * @param id
     * @return
     */
    @Override
    public List<Address> getAddresses(BigInteger id) {

        List<Address> addresses = addressRepository.findAll(Example.of(new Address(id)));

        return addresses;
    }

}
