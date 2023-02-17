package typeqast.business.mapper.impl;

import org.springframework.stereotype.Service;

import typeqast.business.mapper.MeterMapperService;
import typeqast.entities.Meter;
import typeqast.entities.dto.MeterDTO;

@Service
public class MeterMapperServiceImpl implements MeterMapperService {

    @Override
    public Meter toMeter(MeterDTO meterDTO) {

        Meter meter = new Meter();
        meter.setId(meterDTO.getId());
        meter.setName(meterDTO.getName());
        meter.setClient(meterDTO.getClient());
        meter.setReadings(meterDTO.getReadings());

        return meter;
    }

    @Override
    public MeterDTO toMeterDTO(Meter meter) {

        MeterDTO meterDTO = new MeterDTO();
        meterDTO.setId(meter.getId());
        meterDTO.setName(meter.getName());
        meterDTO.setClient(meter.getClient());
        meterDTO.setReadings(meter.getReadings());

        return meterDTO;
    }

}
