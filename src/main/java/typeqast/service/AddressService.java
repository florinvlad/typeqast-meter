package typeqast.service;

import typeqast.entities.dto.AddressDTO;
import typeqast.entities.exception.AddressNotFoundException;
import typeqast.entities.exception.ClientNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface AddressService {

    AddressDTO addAddress(AddressDTO address, BigInteger clientId) throws ClientNotFoundException;

    AddressDTO updateAddress(AddressDTO address, BigInteger clientId) throws ClientNotFoundException, AddressNotFoundException;

    List<AddressDTO> getAddresses(BigInteger id);

}
