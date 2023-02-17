package typeqast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.mapper.AddressMapper;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.dto.AddressDTO;
import typeqast.entities.exception.AddressNotFoundException;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation for {@link AddressService}
 */
@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    private final ClientRepository clientRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, ClientRepository clientRepository) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }


    /**
     * @param addressDTO x
     * @param clientId   y
     * @return z
     */
    public AddressDTO addAddress(AddressDTO addressDTO, BigInteger clientId) throws ClientNotFoundException {

        logger.info("Received add address request");

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) throw new ClientNotFoundException();

        Address saveAddress;

        saveAddress = new Address(addressDTO.getCountry(), addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getNumber());
        saveAddress.setClient(resultClient.get());

        saveAddress = addressRepository.save(saveAddress);

        return AddressMapper.addressToAddressDTO(saveAddress);
    }

    /**
     * @param addressDTO x
     * @param clientId   y
     * @return z
     */
    public AddressDTO updateAddress(AddressDTO addressDTO, BigInteger clientId) throws ClientNotFoundException, AddressNotFoundException {

        Optional<Address> resultAddress = addressRepository.findOne(Example.of(Address.builder().id(addressDTO.getId()).build()));
        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) throw new ClientNotFoundException();
        if (!resultAddress.isPresent()) throw new AddressNotFoundException();

        addressDTO.setClient(resultClient.get());

        Address saveAddress = addressRepository.save(AddressMapper.addressDTOtoAddress(addressDTO));

        return AddressMapper.addressToAddressDTO(saveAddress);
    }

    /**
     * @param id x
     * @return x
     */
    public List<AddressDTO> getAddresses(BigInteger id) {

        List<Address> addresses = addressRepository.findAll(Example.of(Address.builder().id(id).build()));

        return addresses.stream().map(AddressMapper::addressToAddressDTO).collect(Collectors.toList());
    }

}
