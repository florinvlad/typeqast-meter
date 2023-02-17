package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import typeqast.business.mapper.AddressMapperService;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.dto.AddressDTO;
import typeqast.entities.exception.AddressNotFoundException;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.service.AddressService;

import java.math.BigInteger;
import java.util.ArrayList;
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

    @Autowired
    private AddressMapperService addressMapperService;

    /**
     * Implementation for {@link AddressService#addAddress(AddressDTO, BigInteger)}
     *
     * @param addressDTO
     * @param clientId
     * @return
     */
    @Override
    public AddressDTO addAddress(AddressDTO addressDTO, BigInteger clientId) throws ClientNotFoundException {

        logger.info("Received add address request");

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) throw new ClientNotFoundException();

        Address saveAddress;

        saveAddress = new Address(addressDTO.getCountry(), addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getNumber());
        saveAddress.setClient(resultClient.get());

        saveAddress = addressRepository.save(saveAddress);

        AddressDTO saveAddressDTO = addressMapperService.toAddressDTO(saveAddress);

        return saveAddressDTO;
    }

    /**
     * Implementation for {@link AddressService#updateAddress(AddressDTO, BigInteger)}
     *
     * @param addressDTO
     * @param clientId
     * @return
     */
    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, BigInteger clientId) throws ClientNotFoundException, AddressNotFoundException {

        BigInteger addressId = addressDTO.getId();

        Optional<Address> resultAddress = addressRepository.findOne(Example.of(new Address(addressId)));
        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) throw new ClientNotFoundException();
        if (!resultAddress.isPresent()) throw new AddressNotFoundException();

        addressDTO.setClient(resultClient.get());

        Address saveAddress = addressRepository.save(addressMapperService.toAddress(addressDTO));

        return addressMapperService.toAddressDTO(saveAddress);
    }

    /**
     * Implementation for {@link AddressService#getAddresses(BigInteger)}
     *
     * @param id
     * @return
     */
    @Override
    public List<AddressDTO> getAddresses(BigInteger id) {

        List<Address> addresses = addressRepository.findAll(Example.of(new Address(id)));

        List<AddressDTO> addressDTOList = new ArrayList<>();
        for (Address address : addresses) {
            addressDTOList.add(addressMapperService.toAddressDTO(address));
        }

        return addressDTOList;
    }

}
