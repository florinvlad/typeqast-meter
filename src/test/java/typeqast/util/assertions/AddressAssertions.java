package typeqast.util.assertions;

import typeqast.entities.Address;
import typeqast.entities.dto.AddressDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressAssertions {

    public static void execute(AddressDTO expected, AddressDTO actual) {

        assertNotNull(actual, "address should not be null ");
        assertNotNull(actual.getId(), "address id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getNumber(), actual.getNumber());

    }

    public static void execute(Address expected, Address actual) {

        assertNotNull(actual, "address should not be null ");
        assertNotNull(actual.getId(), "address id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getNumber(), actual.getNumber());

    }

}
