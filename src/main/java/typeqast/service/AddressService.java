package typeqast.service;

import typeqast.entities.Address;
import typeqast.entities.exception.AddressNotFoundException;
import typeqast.entities.exception.ClientNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface AddressService {

    Address addAddress(Address address, BigInteger clientId) throws ClientNotFoundException;

    Address updateAddress(Address address, BigInteger clientId) throws ClientNotFoundException, AddressNotFoundException;

    List<Address> getAddresses(BigInteger id);

}
