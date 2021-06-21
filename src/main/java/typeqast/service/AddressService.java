package typeqast.service;

import org.springframework.stereotype.Service;
import typeqast.entities.Address;
import typeqast.entities.response.AddressResponse;

import java.math.BigInteger;
import java.util.List;

@Service
public interface AddressService {

    AddressResponse addAddress(Address address, BigInteger clientId);

    AddressResponse updateAddress(Address address, BigInteger clientId);

    List<Address> getAddresses(BigInteger id);

}
