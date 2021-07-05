package typeqast.business.transformer;

import org.springframework.stereotype.Service;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;

@Service
public interface ReadingMapperService {

    Reading toReading(ReadingDTO readingDTO);

    ReadingDTO toReadingDTO(Reading reading);

}
