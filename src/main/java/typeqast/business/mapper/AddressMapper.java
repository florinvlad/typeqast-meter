package typeqast.business.mapper;

import typeqast.entities.Address;
import typeqast.entities.dto.AddressDTO;

public class AddressMapper{
    private AddressMapper(){}
    public static Address addressDTOtoAddress(AddressDTO addressDTO) {

        Address address = new Address();
        address.setClient(addressDTO.getClient());
        address.setId(addressDTO.getId());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());

        return address;

    }

    public static AddressDTO addressToAddressDTO(Address address) {

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