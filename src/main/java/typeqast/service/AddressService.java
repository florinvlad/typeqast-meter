package typeqast.service;

import typeqast.entities.Address;

import java.math.BigInteger;
import java.util.List;

public interface AddressService {

    Address addAddress(Address address, BigInteger clientId);

    Address updateAddress(Address address, BigInteger clientId);

    List<Address> getAddresses(BigInteger id);

}
