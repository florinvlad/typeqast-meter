package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.ReadingProcessor;
import typeqast.entities.AggregateReading;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.service.MeterService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class MeterServiceImpl implements MeterService {

    private static Logger logger = LoggerFactory.getLogger(MeterServiceImpl.class);

    @Autowired
    @Qualifier("aggregateReading")
    private ReadingProcessor readingProcessor;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Meter addMeter(Meter meter, BigInteger clientId) {

        logger.info("Received add meter request");

        Meter saveMeter = null;

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (resultClient.isPresent()) {
            saveMeter = new Meter(meter.getName());
            saveMeter.setClient(resultClient.get());
            saveMeter = meterRepository.save(saveMeter);
        }

        return saveMeter;
    }

    @Override
    public Meter updateMeter(Meter updateMeter, BigInteger clientId) {

        logger.info("Received update meter request");

        BigInteger id = updateMeter.getId();

        Meter saveMeter = null;

        if (id != null) {
            Optional<Client> readClient = clientRepository.findOne(Example.of(new Client(clientId)));
            Optional<Meter> readMetter = meterRepository.findOne(Example.of(new Meter(id)));

            if (readMetter.isPresent() && (readClient.isPresent())) {

                updateMeter.setClient(readClient.get());
                saveMeter = meterRepository.save(updateMeter);

            }
        }

        return saveMeter;
    }

    @Override
    public List<Meter> getMeters(BigInteger meterId) {

        logger.info("Received get meters request");

        List<Meter> meters = meterRepository.findAll(Example.of(new Meter(meterId)));

        return meters;
    }

    @Override
    public AggregateReading getAggregateReadings(Integer year, BigInteger meterId) {

        logger.info("Received get aggregate reading request");

        AggregateReading aggregateReading = new AggregateReading(year, 0L);

        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (resultMeter.isPresent()) {
            List<Reading> readings = resultMeter.get().getReadings();
            aggregateReading.setTotal(readingProcessor.process(readings));
        }

        return aggregateReading;
    }

}
