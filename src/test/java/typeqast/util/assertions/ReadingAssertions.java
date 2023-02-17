package typeqast.util.assertions;

import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReadingAssertions {

    public static void execute(Reading expected, Reading actual) {

        assertNotNull(actual, "reading should not be null ");
        assertNotNull(actual.getId(), "reading id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getMonth(), actual.getMonth());
        assertEquals(expected.getValue(), actual.getValue());

    }

    public static void execute(ReadingDTO expected, ReadingDTO actual) {

        assertNotNull(actual, "reading should not be null ");
        assertNotNull(actual.getId(), "reading id should not be null ");
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getMonth(), actual.getMonth());
        assertEquals(expected.getValue(), actual.getValue());

    }

}
