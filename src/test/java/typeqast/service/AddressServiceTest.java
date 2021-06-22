package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.response.AddressResponse;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.service.impl.AddressServiceImpl;
import typeqast.util.assertions.AddressAssertions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class AddressServiceTest {

    @TestConfiguration
    static class ServiceImplTestContextConfiguration {

        @Bean
        public AddressService addressService() {
            return new AddressServiceImpl();
        }

    }

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private ClientRepository clientRepository;

    /**
     * Add address unit test for {@link AddressServiceImpl}
     */
    @Test
    public void addAddressTest() {

        Address requestAddress = new Address("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(addressRepository.save(any())).thenReturn(mockResultAddress);

        AddressResponse addressResponse = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNotNull("Response address object should not be null ", addressResponse);

        AddressAssertions.execute(mockResultAddress, addressResponse.getAddress());

    }

    /**
     * Update address unit test for {@link AddressServiceImpl}
     */
    @Test
    public void updateAddressTest() {

        Address requestAddress = new Address("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(addressRepository.save(any())).thenReturn(mockResultAddress);

        AddressResponse addressResponse = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNotNull("Response address object should not be null ", addressResponse);

        AddressAssertions.execute(mockResultAddress, addressResponse.getAddress());

        mockResultAddress = new Address("country1_updated", "city1_updated", "street1_updated", 2);
        mockResultAddress.setId(BigInteger.valueOf(1));

        requestAddress.setId(mockResultAddress.getId());


        Mockito.when(addressRepository.findOne(any())).thenReturn(Optional.of(mockResultAddress));
        Mockito.when(addressRepository.save(any())).thenReturn(mockResultAddress);

        addressResponse = addressService.updateAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNotNull("Response address object should not be null ", addressResponse);

        AddressAssertions.execute(mockResultAddress, addressResponse.getAddress());

    }

    /**
     * Get addresses unit test for {@link AddressServiceImpl}
     */

    @Test
    public void getAddressesTest() {

        Address address1 = new Address("country1", "city1", "street1", 1);
        Address address2 = new Address("country2", "city2", "street2", 2);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);

        Mockito.when(addressRepository.findAll(any(Example.class))).thenReturn(addressList);

        List<Address> resultAddressList = addressService.getAddresses(null);

        Assert.assertNotNull(resultAddressList);
        Assert.assertEquals(resultAddressList.size(), 2);


    }

    /**
     * Add address address to a client that already has one assigned
     */
    @Test
    public void addAddressAlreadyAsignedTest() {

        Address requestAddress = new Address("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.of(new Client(BigInteger.valueOf(1))));
        Mockito.when(addressRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        AddressResponse addressResponse = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNull("Response address object should be null ", addressResponse.getAddress());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, addressResponse.getStatus());

    }

    /**
     * Add address for inexistent client
     */
    @Test
    public void addAddressInexistentClientTest() {

        Address requestAddress = new Address("country1", "city1", "street1", 1);

        Address mockResultAddress = new Address("country1", "city1", "street1", 1);
        mockResultAddress.setId(BigInteger.valueOf(1));

        Mockito.when(clientRepository.findOne(any(Example.class))).thenReturn(Optional.empty());

        AddressResponse addressResponse = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNull("Response address object should be null ", addressResponse.getAddress());
        Assert.assertEquals(HttpStatus.NOT_FOUND, addressResponse.getStatus());

    }

}
