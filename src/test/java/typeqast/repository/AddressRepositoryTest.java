package typeqast.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Address;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @After
    public void afterTest() {
        addressRepository.deleteAll();
    }

    @Test
    public void addAddressTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        Assert.assertNotNull(resultAddress.getId());
        Assert.assertEquals(address.getCountry(), resultAddress.getCountry());
        Assert.assertEquals(address.getCity(), resultAddress.getCity());
        Assert.assertEquals(address.getStreet(), resultAddress.getStreet());
        Assert.assertEquals(address.getNumber(), resultAddress.getNumber());

    }

    @Test
    public void updateAddressTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        Assert.assertNotNull(resultAddress.getId());
        Assert.assertEquals(address.getCountry(), resultAddress.getCountry());
        Assert.assertEquals(address.getCity(), resultAddress.getCity());
        Assert.assertEquals(address.getStreet(), resultAddress.getStreet());
        Assert.assertEquals(address.getNumber(), resultAddress.getNumber());

        address = resultAddress;
        address.setCountry("country1_updated");
        address.setCity("city1_updated");
        address.setNumber(11);

        resultAddress = addressRepository.save(address);
        Assert.assertNotNull(resultAddress.getId());
        Assert.assertEquals(address.getCountry(), resultAddress.getCountry());
        Assert.assertEquals(address.getCity(), resultAddress.getCity());
        Assert.assertEquals(address.getStreet(), resultAddress.getStreet());
        Assert.assertEquals(address.getNumber(), resultAddress.getNumber());


        Optional<Address> resultAddress2 = addressRepository.findOne(Example.of(address));

        Assert.assertTrue(resultAddress2.isPresent());
        Assert.assertEquals(address.getCountry(), resultAddress.getCountry());
        Assert.assertEquals(address.getCity(), resultAddress.getCity());
        Assert.assertEquals(address.getStreet(), resultAddress.getStreet());
        Assert.assertEquals(address.getNumber(), resultAddress.getNumber());

    }

    @Test
    public void getAddresssTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        Assert.assertNotNull(resultAddress);
        Assert.assertNotNull(resultAddress.getId());
        Assert.assertEquals(address.getCountry(), resultAddress.getCountry());
        Assert.assertEquals(address.getCity(), resultAddress.getCity());
        Assert.assertEquals(address.getStreet(), resultAddress.getStreet());
        Assert.assertEquals(address.getNumber(), resultAddress.getNumber());

        Address address2 = new Address("country2", "city2", "street2", 2);

        resultAddress = addressRepository.save(address2);

        Assert.assertNotNull(resultAddress);
        Assert.assertNotNull(resultAddress.getId());
        Assert.assertEquals(address2.getCountry(), resultAddress.getCountry());
        Assert.assertEquals(address2.getCity(), resultAddress.getCity());
        Assert.assertEquals(address2.getStreet(), resultAddress.getStreet());
        Assert.assertEquals(address2.getNumber(), resultAddress.getNumber());

        List<Address> addressResultList = addressRepository.findAll();

        Assert.assertNotNull(addressResultList);
        Assert.assertEquals(addressResultList.size(), 2);

    }

}
