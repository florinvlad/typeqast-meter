package typeqast.service;

import typeqast.entities.AggregateReading;
import typeqast.entities.Meter;
import typeqast.entities.dto.MeterDTO;
import typeqast.entities.exception.ClientNotFoundException;
import typeqast.entities.exception.MeterNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface MeterService {

    MeterDTO addMeter(MeterDTO meterDTO, BigInteger clientId) throws ClientNotFoundException;

    MeterDTO updateMeter(MeterDTO meterDTO, BigInteger clientId) throws ClientNotFoundException, MeterNotFoundException;

    List<MeterDTO> getMeters(BigInteger meterId);

    AggregateReading getAggregateReadings(Integer year, BigInteger meterId);


}
