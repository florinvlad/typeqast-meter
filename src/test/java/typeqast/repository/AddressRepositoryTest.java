package typeqast.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import typeqast.entities.Address;
import typeqast.util.assertions.AddressAssertions;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    public void afterEach() {
        addressRepository.deleteAll();
    }

    @Test
    void addAddressTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        AddressAssertions.execute(address, resultAddress);
        Assertions.assertTrue(true);

    }

    @Test
    void updateAddressTest() {

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

        assertTrue(resultAddress2.isPresent());

        AddressAssertions.execute(address, resultAddress2.get());

    }

    @Test
    void getAddressesTest() {

        Address address = new Address("country1", "city1", "street1", 1);

        Address resultAddress = addressRepository.save(address);

        AddressAssertions.execute(address,resultAddress);

        Address address2 = new Address("country2", "city2", "street2", 2);

        resultAddress = addressRepository.save(address2);

        AddressAssertions.execute(address2,resultAddress);

        List<Address> addressResultList = addressRepository.findAll();

        assertNotNull(addressResultList);
        assertEquals(2,addressResultList.size());

    }

}
