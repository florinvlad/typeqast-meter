package typeqast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.business.mapper.ReadingMapper;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;
import typeqast.entities.exception.MeterNotFoundException;
import typeqast.entities.exception.ReadingNotFoundException;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link ReadingService}
 */
@Service
public class ReadingService{

    private static final Logger logger = LoggerFactory.getLogger(ReadingService.class);

    @Autowired
    public ReadingService(ReadingRepository readingRepository, MeterRepository meterRepository){
        this.readingRepository=readingRepository;
        this.meterRepository=meterRepository;
    }

    private final ReadingRepository readingRepository;

    private final MeterRepository meterRepository;


    public ReadingDTO addReading(ReadingDTO readingDTO, BigInteger meterId) throws MeterNotFoundException {

        logger.info("Received add reading request");


        Optional<Meter> resultMeter = meterRepository.findOne(Example.of(new Meter(meterId)));

        if (!resultMeter.isPresent()) {
            throw new MeterNotFoundException();
        }

        readingDTO.setMeter(resultMeter.get());

        Reading saveReading = ReadingMapper.toReading(readingDTO);

        saveReading = readingRepository.save(saveReading);

        return ReadingMapper.toReadingDTO(saveReading);

    }

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

        Reading saveReading = ReadingMapper.toReading(readingDTO);

        saveReading = readingRepository.save(saveReading);

        return ReadingMapper.toReadingDTO(saveReading);
    }

    public List<ReadingDTO> getReadings() {
        logger.info("Received get readings request");

        List<Reading> readingList = readingRepository.findAll();
        List<ReadingDTO> readingDTOList = new ArrayList<>();
        for (Reading reading : readingList) {
            readingDTOList.add(ReadingMapper.toReadingDTO(reading));
        }

        return readingDTOList;
    }

}