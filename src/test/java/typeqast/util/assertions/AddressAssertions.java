package typeqast.util.assertions;

import org.junit.Assert;
import typeqast.entities.Address;

public class AddressAssertions {

    public static void execute(Address expected, Address actual) {

        Assert.assertNotNull("address should not be null ", actual);
        Assert.assertNotNull("address id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getCountry(), actual.getCountry());
        Assert.assertEquals(expected.getCity(), actual.getCity());
        Assert.assertEquals(expected.getStreet(), actual.getStreet());
        Assert.assertEquals(expected.getNumber(), actual.getNumber());

    }

}
