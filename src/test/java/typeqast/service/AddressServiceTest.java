package typeqast.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import typeqast.business.mapper.AddressMapper;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.dto.AddressDTO;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.util.assertions.AddressAssertions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void addAddressTest() {

        AddressDTO requestAddressDTO = new AddressDTO("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(addressRepository.save(any())).thenReturn(mockResultAddress);

        AddressDTO resultAddressDTO = addressService.addAddress(requestAddressDTO, BigInteger.valueOf(1));

        Assertions.assertNotNull(resultAddressDTO, " address object should not be null ");

        AddressAssertions.execute(AddressMapper.addressToAddressDTO(mockResultAddress), resultAddressDTO);

    }

    @Test
    void updateAddressTest() {

        AddressDTO requestAddress = new AddressDTO("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(addressRepository.save(any())).thenReturn(mockResultAddress);

        AddressDTO resultAddressDTO = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assertions.assertNotNull(resultAddressDTO, " address object should not be null ");

        AddressDTO expectedAddressDTO = AddressMapper.addressToAddressDTO(mockResultAddress);

        AddressAssertions.execute(expectedAddressDTO, resultAddressDTO);

        mockResultAddress = new Address("country1_updated", "city1_updated", "street1_updated", 2);
        mockResultAddress.setId(BigInteger.valueOf(1));

        requestAddress.setId(mockResultAddress.getId());


        Mockito.when(addressRepository.findOne(any())).thenReturn(Optional.of(mockResultAddress));
        Mockito.when(addressRepository.save(any())).thenReturn(mockResultAddress);

        resultAddressDTO = addressService.updateAddress(requestAddress, BigInteger.valueOf(1));

        Assertions.assertNotNull(resultAddressDTO, " address object should not be null");

        expectedAddressDTO = AddressMapper.addressToAddressDTO(mockResultAddress);
        AddressAssertions.execute(expectedAddressDTO, resultAddressDTO);

    }

    @Test
    void getAddressesTest() {

        Address address1 = new Address("country1", "city1", "street1", 1);
        Address address2 = new Address("country2", "city2", "street2", 2);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);

        Mockito.when(addressRepository.findAll(any(Example.class))).thenReturn(addressList);

        List<AddressDTO> resultAddressList = addressService.getAddresses(null);

        Assertions.assertNotNull(resultAddressList);
        Assertions.assertEquals(2, resultAddressList.size());


    }

    /**
     * Add address for inexistent client
     */
    @Test
    void addAddressInexistentClientTest() {

        AddressDTO requestAddress = new AddressDTO("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.empty());

        try {
            addressService.addAddress(requestAddress, BigInteger.valueOf(1));
        } catch (ClientNotFoundException cnfe) {
            Assertions.assertNotNull(cnfe);
        }

    }

}
