package typeqast.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Meter;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeterRepositoryTest {

    @Autowired
    private MeterRepository meterRepository;

    @After
    public void afterTest() {
        meterRepository.deleteAll();
    }

    @Test
    public void addMeterTest() {

        Meter meter = new Meter("meter1");

        Meter resultMeter = meterRepository.save(meter);

        Assert.assertNotNull(resultMeter.getId());
        Assert.assertEquals(meter.getName(), resultMeter.getName());

    }

    @Test
    public void updateMeterTest() {

        Meter meter = new Meter("meter1");

        Meter resultMeter = meterRepository.save(meter);

        Assert.assertNotNull(resultMeter.getId());
        Assert.assertEquals(meter.getName(), resultMeter.getName());

        meter = resultMeter;
        meter.setName("meter1_updated");

        resultMeter = meterRepository.save(meter);
        Assert.assertNotNull(resultMeter.getId());
        Assert.assertEquals(meter.getName(), resultMeter.getName());

        Optional<Meter> resultMeter2 = meterRepository.findOne(Example.of(meter));

        Assert.assertTrue(resultMeter2.isPresent());
        Assert.assertNotNull(resultMeter.getId());
        Assert.assertEquals(meter.getName(), resultMeter.getName());

    }

    @Test
    public void getMetersTest() {

        Meter meter = new Meter("meter1");

        Meter resultMeter = meterRepository.save(meter);

        Assert.assertNotNull(resultMeter);
        Assert.assertNotNull(resultMeter.getId());
        Assert.assertEquals(meter.getName(), resultMeter.getName());

        Meter meter2 = new Meter("meter2");

        resultMeter = meterRepository.save(meter2);

        Assert.assertNotNull(resultMeter);
        Assert.assertNotNull(resultMeter.getId());
        Assert.assertEquals(meter2.getName(), resultMeter.getName());

        List<Meter> meterResultList = meterRepository.findAll();

        Assert.assertNotNull(meterResultList);
        Assert.assertEquals(meterResultList.size(), 2);

    }

}
