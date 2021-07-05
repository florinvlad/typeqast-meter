package typeqast.util.assertions;

import org.junit.Assert;
import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;

public class ClientAssertions {

    public static void execute(Client expected, Client actual) {

        Assert.assertNotNull("client should not be null ", actual);
        Assert.assertNotNull("client id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());

    }

    public static void execute(ClientDTO expected, ClientDTO actual) {

        Assert.assertNotNull("client should not be null ", actual);
        Assert.assertNotNull("client id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());

    }

}
