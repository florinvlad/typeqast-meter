package typeqast.service;

import org.springframework.stereotype.Service;
import typeqast.entities.Meter;
import typeqast.entities.response.MeterResponse;

import java.math.BigInteger;
import java.util.List;

@Service
public interface MeterService {

    MeterResponse addMeter(Meter meter, BigInteger clientId);

    MeterResponse updateMeter(Meter meter, BigInteger clientId);

    List<Meter> getMeters(BigInteger meterId);

}
