package typeqast.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import typeqast.entities.Meter;
import typeqast.util.assertions.MeterAssertions;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class MeterRepositoryTest {

    @Autowired
    private MeterRepository meterRepository;

    @AfterEach
    public void afterEachTest() {
        meterRepository.deleteAll();
    }

    @Test
    public void addMeterTest() {

        Meter meter = new Meter("meter1");

        Meter resultMeter = meterRepository.save(meter);

        MeterAssertions.execute(meter, resultMeter);

    }

    @Test
    public void updateMeterTest() {

        Meter meter = new Meter("meter1");

        Meter resultMeter = meterRepository.save(meter);

        MeterAssertions.execute(meter, resultMeter);

        meter = resultMeter;
        meter.setName("meter1_updated");

        resultMeter = meterRepository.save(meter);

        MeterAssertions.execute(meter, resultMeter);

        Optional<Meter> resultMeter2 = meterRepository.findOne(Example.of(meter));

        Assertions.assertTrue(resultMeter2.isPresent());

        MeterAssertions.execute(meter, resultMeter2.get());

    }

    @Test
    public void getMetersTest() {

        Meter meter = new Meter("meter1");

        Meter resultMeter = meterRepository.save(meter);

        MeterAssertions.execute(meter, resultMeter);

        Meter meter2 = new Meter("meter2");

        resultMeter = meterRepository.save(meter2);

        MeterAssertions.execute(meter2, resultMeter);

        List<Meter> meterResultList = meterRepository.findAll();

        Assertions.assertNotNull(meterResultList);
        Assertions.assertEquals(meterResultList.size(), 2);

    }

}
