package typeqast.util.assertions;

import org.junit.Assert;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;

public class ReadingAssertions {

    public static void execute(Reading expected, Reading actual) {

        Assert.assertNotNull("reading should not be null ", actual);
        Assert.assertNotNull("reading id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getYear(), actual.getYear());
        Assert.assertEquals(expected.getMonth(), actual.getMonth());
        Assert.assertEquals(expected.getValue(), actual.getValue());

    }

    public static void execute(ReadingDTO expected, ReadingDTO actual) {

        Assert.assertNotNull("reading should not be null ", actual);
        Assert.assertNotNull("reading id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getYear(), actual.getYear());
        Assert.assertEquals(expected.getMonth(), actual.getMonth());
        Assert.assertEquals(expected.getValue(), actual.getValue());

    }

}
