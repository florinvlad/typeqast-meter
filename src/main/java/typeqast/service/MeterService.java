package typeqast.service;

import typeqast.entities.Meter;

import java.math.BigInteger;
import java.util.List;

public interface MeterService {

    Meter addMeter(Meter meter, BigInteger clientId);

    Meter updateMeter(Meter meter, BigInteger clientId);

    List<Meter> getMeters(BigInteger meterId);

}
