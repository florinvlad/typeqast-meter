package typeqast.business.transformer;

import org.springframework.stereotype.Service;
import typeqast.entities.Meter;
import typeqast.entities.dto.MeterDTO;

@Service
public interface MeterMapperService {

    Meter toMeter(MeterDTO meterDTO);

    MeterDTO toMeterDTO(Meter meter);

}
