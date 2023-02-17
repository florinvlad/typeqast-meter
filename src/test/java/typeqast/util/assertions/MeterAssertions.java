package typeqast.util.assertions;

import typeqast.entities.Meter;
import typeqast.entities.dto.MeterDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeterAssertions {

    public static void execute(Meter expected, Meter actual) {

        assertNotNull(actual,"meter should not be null ");
        assertNotNull(actual.getId(),"meter id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

    }

    public static void execute(MeterDTO expected, MeterDTO actual) {

        assertNotNull(actual,"meter should not be null ");
        assertNotNull(actual.getId(),"meter id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

    }

}
