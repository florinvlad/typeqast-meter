package typeqast.business.mapper;

import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;

public class ReadingMapper {

    private ReadingMapper() {
    }

    public static Reading toReading(ReadingDTO readingDTO) {

        Reading reading = new Reading();
        reading.setId(readingDTO.getId());
        reading.setValue(readingDTO.getValue());
        reading.setYear(readingDTO.getYear());
        reading.setMonth(readingDTO.getMonth());
        reading.setMeter(readingDTO.getMeter());

        return reading;
    }

    public static ReadingDTO toReadingDTO(Reading reading) {

        ReadingDTO readingDTO = new ReadingDTO();
        readingDTO.setId(reading.getId());
        readingDTO.setValue(reading.getValue());
        readingDTO.setYear(reading.getYear());
        readingDTO.setMonth(reading.getMonth());
        readingDTO.setMeter(reading.getMeter());

        return readingDTO;
    }

}
