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
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.service.MeterService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link MeterService}
 */
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

    /**
     * Implementation for {@link MeterService#addMeter(Meter, BigInteger)}
     */
    @Override
    public Meter addMeter(Meter meter, BigInteger clientId) throws ClientNotFoundException {

        logger.info("Received add meter request");

        Meter saveMeter;

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) {
            throw new ClientNotFoundException();
        }

        saveMeter = new Meter(meter.getName());
        saveMeter.setClient(resultClient.get());
        saveMeter = meterRepository.save(saveMeter);


        return saveMeter;
    }

    /**
     * Implementation for {@link MeterService#updateMeter(Meter, BigInteger)}
     */
    @Override
    public Meter updateMeter(Meter updateMeter, BigInteger clientId) throws ClientNotFoundException, MeterNotFoundException {

        logger.info("Received update meter request");

        BigInteger meterId = updateMeter.getId();

        Meter saveMeter;

        Optional<Client> readClient = clientRepository.findOne(Example.of(new Client(clientId)));
        Optional<Meter> readMetter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!readMetter.isPresent()) {
            throw new MeterNotFoundException();
        }

        if (!readClient.isPresent()) {
            throw new ClientNotFoundException();
        }

        updateMeter.setClient(readClient.get());
        saveMeter = meterRepository.save(updateMeter);


        return saveMeter;
    }

    /**
     * Implementation for {@link MeterService#getMeters(BigInteger)}
     */
    @Override
    public List<Meter> getMeters(BigInteger meterId) {

        logger.info("Received get meters request");

        List<Meter> meters = meterRepository.findAll(Example.of(new Meter(meterId)));

        return meters;
    }

    /**
     * Implementation for {@link MeterService#getAggregateReadings(Integer, BigInteger)}
     */
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
