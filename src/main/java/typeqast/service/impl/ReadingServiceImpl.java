package typeqast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.transformer.ReadingMapperService;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.entities.exception.ReadingNotFoundException;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;
import typeqast.service.ReadingService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link ReadingService}
 */
@Service
public class ReadingServiceImpl implements ReadingService {

    private static Logger logger = LoggerFactory.getLogger(ReadingServiceImpl.class);

    @Autowired
    private ReadingMapperService readingMapperService;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MeterRepository meterRepository;

    /**
     * Implementation for {@link ReadingService#addReading(ReadingDTO, BigInteger)}
     */
    @Override
    public ReadingDTO addReading(ReadingDTO readingDTO, BigInteger meterId) throws MeterNotFoundException {

        logger.info("Received add reading request");


        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!resultMeter.isPresent()) {
            throw new MeterNotFoundException();
        }

        readingDTO.setMeter(resultMeter.get());

        Reading saveReading = readingMapperService.toReading(readingDTO);

        saveReading = readingRepository.save(saveReading);

        return readingMapperService.toReadingDTO(saveReading);

    }

    /**
     * Implementation for {@link ReadingService#updateReading(ReadingDTO, BigInteger)}
     */
    @Override
    public ReadingDTO updateReading(ReadingDTO readingDTO, BigInteger meterId) throws MeterNotFoundException, ReadingNotFoundException {

        logger.info("Received update reading request");


        BigInteger id = readingDTO.getId();

        Optional<Reading> resultReading = readingRepository.findOne(Example.of(new Reading(id)));
        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!resultMeter.isPresent()) {
            throw new MeterNotFoundException();
        }

        if (!resultReading.isPresent()) {
            throw new ReadingNotFoundException();
        }

        readingDTO.setMeter(resultMeter.get());

        Reading saveReading = readingMapperService.toReading(readingDTO);

        saveReading = readingRepository.save(saveReading);

        return readingMapperService.toReadingDTO(saveReading);
    }

    /**
     * Implementation for {@link ReadingService#getReadings()}
     */
    @Override
    public List<ReadingDTO> getReadings() {
        logger.info("Received get readings request");

        List<Reading> readingList = readingRepository.findAll();
        List<ReadingDTO> readingDTOList = new ArrayList<>();
        for (Reading reading : readingList) {
            readingDTOList.add(readingMapperService.toReadingDTO(reading));
        }

        return readingDTOList;
    }

}
