package typeqast.business.mapper;

import typeqast.entities.Address;
import typeqast.entities.dto.AddressDTO;

public class AddressMapper {
    private AddressMapper() {
    }

    public static Address addressDTOtoAddress(AddressDTO addressDTO) {

        return Address.builder()
                .id(addressDTO.getId())
                .client(addressDTO.getClient())
                .number(addressDTO.getNumber())
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .country(addressDTO.getCountry())
                .build();

    }

    public static AddressDTO addressToAddressDTO(Address address) {

        return AddressDTO.builder()
                .id(address.getId())
                .client(address.getClient())
                .number(address.getNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .build();

    }
}