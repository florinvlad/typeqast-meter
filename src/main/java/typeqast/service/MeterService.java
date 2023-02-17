package typeqast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.mapper.MeterMapper;
import typeqast.business.processor.ReadingProcessor;
import typeqast.entities.AggregateReading;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.MeterDTO;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link MeterService}
 */
@Service
public class MeterService {

    private static final Logger logger = LoggerFactory.getLogger(MeterService.class);

    @Qualifier("aggregateReading")
    private ReadingProcessor readingProcessor;

    public MeterService(MeterRepository meterRepository,ClientRepository clientRepository,ReadingProcessor readingProcessor){
        this.readingProcessor=readingProcessor;
        this.meterRepository=meterRepository;
        this.clientRepository=clientRepository;
    }

    private final MeterRepository meterRepository;

    private final ClientRepository clientRepository;

    public MeterDTO addMeter(MeterDTO meterDTO, BigInteger clientId) throws ClientNotFoundException {

        logger.info("Received add meter request");

        Meter saveMeter;

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (!resultClient.isPresent()) {
            throw new ClientNotFoundException();
        }

        saveMeter = new Meter(meterDTO.getName());
        saveMeter.setClient(resultClient.get());
        saveMeter = meterRepository.save(saveMeter);

        return MeterMapper.toMeterDTO(saveMeter);
    }

    public MeterDTO updateMeter(MeterDTO updateMeterDTO, BigInteger clientId) throws ClientNotFoundException, MeterNotFoundException {

        logger.info("Received update meter request");

        BigInteger meterId = updateMeterDTO.getId();

        Optional<Client> readClient = clientRepository.findOne(Example.of(new Client(clientId)));
        Optional<Meter> readMetter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!readMetter.isPresent()) {
            throw new MeterNotFoundException();
        }

        if (!readClient.isPresent()) {
            throw new ClientNotFoundException();
        }

        updateMeterDTO.setClient(readClient.get());

        Meter saveMeter = MeterMapper.toMeter(updateMeterDTO);

        saveMeter = meterRepository.save(saveMeter);

        return MeterMapper.toMeterDTO(saveMeter);
    }

    public List<MeterDTO> getMeters(BigInteger meterId) {

        logger.info("Received get meters request");

        List<Meter> meters = meterRepository.findAll(Example.of(new Meter(meterId)));

        List<MeterDTO> meterDTOList = new ArrayList<>();

        for (Meter meter : meters) {
            meterDTOList.add(MeterMapper.toMeterDTO(meter));
        }

        return meterDTOList;
    }

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
