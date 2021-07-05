package typeqast.util.assertions;

import org.junit.Assert;
import typeqast.entities.Meter;
import typeqast.entities.dto.MeterDTO;

public class MeterAssertions {

    public static void execute(Meter expected, Meter actual) {

        Assert.assertNotNull("meter should not be null ", actual);
        Assert.assertNotNull("meter id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());

    }

    public static void execute(MeterDTO expected, MeterDTO actual) {

        Assert.assertNotNull("meter should not be null ", actual);
        Assert.assertNotNull("meter id should not be null ", actual.getId());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());

    }

}
