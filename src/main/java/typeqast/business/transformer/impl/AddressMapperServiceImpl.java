package typeqast.business.transformer.impl;

import org.springframework.stereotype.Service;
import typeqast.business.transformer.AddressMapperService;
import typeqast.entities.Address;
import typeqast.entities.dto.AddressDTO;

/**
 * Implementation for {@link AddressMapperService}
 */
@Service
public class AddressMapperServiceImpl implements AddressMapperService {

    @Override
    public Address toAddress(AddressDTO addressDTO) {

        Address address = new Address();
        address.setClient(addressDTO.getClient());
        address.setId(addressDTO.getId());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());

        return address;

    }

    @Override
    public AddressDTO toAddressDTO(Address address) {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setClient(address.getClient());
        addressDTO.setId(address.getId());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());

        return addressDTO;

    }
}
