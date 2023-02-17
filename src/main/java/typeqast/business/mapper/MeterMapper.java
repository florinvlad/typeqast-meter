package typeqast.business.mapper;

import typeqast.entities.Meter;
import typeqast.entities.dto.MeterDTO;

public class MeterMapper {

    private MeterMapper() {
    }


    public static Meter toMeter(MeterDTO meterDTO) {

        Meter meter = new Meter();
        meter.setId(meterDTO.getId());
        meter.setName(meterDTO.getName());
        meter.setClient(meterDTO.getClient());
        meter.setReadings(meterDTO.getReadings());

        return meter;
    }

    public static MeterDTO toMeterDTO(Meter meter) {

        MeterDTO meterDTO = new MeterDTO();
        meterDTO.setId(meter.getId());
        meterDTO.setName(meter.getName());
        meterDTO.setClient(meter.getClient());
        meterDTO.setReadings(meter.getReadings());

        return meterDTO;
    }

}
