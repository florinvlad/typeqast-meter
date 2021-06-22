package typeqast.service;

import typeqast.entities.Address;
import typeqast.entities.response.AddressResponse;

import java.math.BigInteger;
import java.util.List;

public interface AddressService {

    AddressResponse addAddress(Address address, BigInteger clientId);

    AddressResponse updateAddress(Address address, BigInteger clientId);

    List<Address> getAddresses(BigInteger id);

}
