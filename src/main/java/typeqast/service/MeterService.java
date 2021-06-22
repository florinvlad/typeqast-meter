package typeqast.service;

import typeqast.entities.Meter;
import typeqast.entities.response.MeterResponse;

import java.math.BigInteger;
import java.util.List;

public interface MeterService {

    MeterResponse addMeter(Meter meter, BigInteger clientId);

    MeterResponse updateMeter(Meter meter, BigInteger clientId);

    List<Meter> getMeters(BigInteger meterId);

}
