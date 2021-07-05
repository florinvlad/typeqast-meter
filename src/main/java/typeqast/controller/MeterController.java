package typeqast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import typeqast.constants.RequestParams;
import typeqast.constants.RestEndpoints;
import typeqast.entities.AggregateReading;
import typeqast.entities.Meter;
import typeqast.service.MeterService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.util.List;

@RestController
@Validated
public class MeterController {

    private static Logger logger = LoggerFactory.getLogger(MeterController.class);

    @Autowired
    private MeterService meterService;


    /**
     * Add new meter to existing client
     *
     * @param meter    json body
     * @param clientId
     * @return
     */
    @PostMapping(RestEndpoints.METERS)
    public ResponseEntity<Meter> addMeter(@RequestBody Meter meter, @RequestParam(name = RequestParams.CLIENT_ID) BigInteger clientId) {

        Meter resultMeter = meterService.addMeter(meter, clientId);

        return new ResponseEntity<>(resultMeter, HttpStatus.CREATED);

    }


    /**
     * Update meter
     *
     * @param meter json body
     * @return
     */
    @PutMapping(RestEndpoints.METERS)
    public ResponseEntity<Meter> updateMeter(@RequestBody Meter meter, @RequestParam(RequestParams.CLIENT_ID) BigInteger clientId, @RequestParam(RequestParams.METER_ID) BigInteger meterId) {

        meter.setId(meterId);
        Meter resultMeter = meterService.updateMeter(meter, clientId);

        return new ResponseEntity<>(resultMeter, HttpStatus.OK);

    }

    /**
     * Get list of meters or single entry
     *
     * @param meterId optional, id of the meter
     * @return
     */
    @GetMapping(RestEndpoints.METERS)
    public ResponseEntity<List<Meter>> getMeters(@RequestParam(name = RequestParams.ID, required = false) BigInteger meterId) {
        logger.info("Received get meters request");

        List<Meter> meters = meterService.getMeters(meterId);

        return new ResponseEntity<>(meters, HttpStatus.OK);
    }

    /**
     * Get aggregate readings for a year
     *
     * @param meterId optional, id for a specific meter
     * @param year    mandatory
     * @return
     */
    @GetMapping(RestEndpoints.METERS + RestEndpoints.AGGREGATE)
    public ResponseEntity<AggregateReading> getAggregateReading(@RequestParam(name = RequestParams.METER_ID) BigInteger meterId
            , @RequestParam(name = RequestParams.YEAR, required = false) @Min(1900) @Max(2021) Integer year) {
        logger.info("Received get aggregate readings request");

        AggregateReading aggregateReadingResult = meterService.getAggregateReadings(year, meterId);

        return new ResponseEntity<>(aggregateReadingResult, HttpStatus.OK);
    }

}
