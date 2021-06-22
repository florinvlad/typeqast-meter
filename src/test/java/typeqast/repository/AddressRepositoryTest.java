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
import typeqast.util.assertions.AddressAssertions;

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

        AddressAssertions.execute(address, resultAddress);

    }

    @Test
    public void updateAddressTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        AddressAssertions.execute(address, resultAddress);

        address = resultAddress;
        address.setCountry("country1_updated");
        address.setCity("city1_updated");
        address.setNumber(11);

        resultAddress = addressRepository.save(address);

        AddressAssertions.execute(address, resultAddress);

        Optional<Address> resultAddress2 = addressRepository.findOne(Example.of(address));

        Assert.assertTrue(resultAddress2.isPresent());

        AddressAssertions.execute(address, resultAddress2.get());

    }

    @Test
    public void getAddresssTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        AddressAssertions.execute(address,resultAddress);

        Address address2 = new Address("country2", "city2", "street2", 2);

        resultAddress = addressRepository.save(address2);

        AddressAssertions.execute(address2,resultAddress);

        List<Address> addressResultList = addressRepository.findAll();

        Assert.assertNotNull(addressResultList);
        Assert.assertEquals(addressResultList.size(), 2);

    }

}
