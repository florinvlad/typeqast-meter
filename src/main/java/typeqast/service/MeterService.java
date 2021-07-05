package typeqast.service;

import typeqast.entities.AggregateReading;
import typeqast.entities.Meter;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.entities.exception.MeterNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface MeterService {

    Meter addMeter(Meter meter, BigInteger clientId) throws ClientNotFoundException;

    Meter updateMeter(Meter meter, BigInteger clientId) throws ClientNotFoundException, MeterNotFoundException;

    List<Meter> getMeters(BigInteger meterId);

    AggregateReading getAggregateReadings(Integer year, BigInteger meterId);


}
