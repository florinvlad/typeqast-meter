package typeqast.business.processor;

import typeqast.entities.Reading;

import java.util.List;

public interface ReadingProcessor {

    Long process(List<Reading> readings);

}
