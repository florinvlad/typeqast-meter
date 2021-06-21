package typeqast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.response.AddressResponse;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.service.impl.AddressServiceImpl;

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
        Mockito.when(addressRepository.save(requestAddress)).thenReturn(mockResultAddress);

        AddressResponse addressResponse = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNotNull("Response address object should not be null ", addressResponse);
        Assert.assertNotNull("Response address should not be null ", addressResponse.getAddress());
        Assert.assertNotNull("Address Id should not be null ", addressResponse.getAddress().getId());
        Assert.assertEquals(requestAddress.getCountry(), addressResponse.getAddress().getCountry());
        Assert.assertEquals(requestAddress.getCity(), addressResponse.getAddress().getCity());
        Assert.assertEquals(requestAddress.getStreet(), addressResponse.getAddress().getStreet());
        Assert.assertEquals(requestAddress.getNumber(), addressResponse.getAddress().getNumber());

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
        Mockito.when(addressRepository.save(requestAddress)).thenReturn(mockResultAddress);

        AddressResponse addressResponse = addressService.addAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNotNull("Response address object should not be null ", addressResponse);
        Assert.assertNotNull("Response address should not be null ", addressResponse.getAddress());
        Assert.assertNotNull("Address Id should not be null ", addressResponse.getAddress().getId());
        Assert.assertEquals(requestAddress.getCountry(), addressResponse.getAddress().getCountry());
        Assert.assertEquals(requestAddress.getCity(), addressResponse.getAddress().getCity());
        Assert.assertEquals(requestAddress.getStreet(), addressResponse.getAddress().getStreet());
        Assert.assertEquals(requestAddress.getNumber(), addressResponse.getAddress().getNumber());

        requestAddress.setCountry("country1_updated");
        requestAddress.setCity("city1_updated");
        requestAddress.setStreet("street1_updated");
        requestAddress.setNumber(11);
        requestAddress.setId(addressResponse.getAddress().getId());

        mockResultAddress.setCountry("country1_updated");
        mockResultAddress.setCity("city1_updated");
        mockResultAddress.setStreet("street1_updated");
        mockResultAddress.setNumber(11);

        Mockito.when(addressRepository.findOne(any())).thenReturn(Optional.of(mockResultAddress));
        Mockito.when(addressRepository.save(requestAddress)).thenReturn(mockResultAddress);

        addressResponse = addressService.updateAddress(requestAddress, BigInteger.valueOf(1));

        Assert.assertNotNull("Response address object should not be null ", addressResponse);
        Assert.assertNotNull("Response address should not be null ", addressResponse.getAddress());
        Assert.assertNotNull("Address Id should not be null ", addressResponse.getAddress().getId());
        Assert.assertEquals(requestAddress.getCountry(), addressResponse.getAddress().getCountry());
        Assert.assertEquals(requestAddress.getCity(), addressResponse.getAddress().getCity());
        Assert.assertEquals(requestAddress.getStreet(), addressResponse.getAddress().getStreet());
        Assert.assertEquals(requestAddress.getNumber(), addressResponse.getAddress().getNumber());


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

}
