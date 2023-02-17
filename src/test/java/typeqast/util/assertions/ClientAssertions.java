package typeqast.util.assertions;

import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientAssertions {

    public static void execute(Client expected, Client actual) {

        assertNotNull(actual, "client should not be null ");
        assertNotNull(actual.getId(), "client id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

    }

    public static void execute(ClientDTO expected, ClientDTO actual) {

        assertNotNull(actual, "client should not be null ");
        assertNotNull(actual.getId(), "client id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

    }

}
