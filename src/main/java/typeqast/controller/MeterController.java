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
import typeqast.entities.Meter;
import typeqast.entities.response.MeterResponse;
import typeqast.service.MeterService;

import javax.validation.ConstraintViolationException;
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

        MeterResponse meterResponse = meterService.addMeter(meter, clientId);

        return new ResponseEntity<>(meterResponse.getMeter(), meterResponse.getStatus());
    }


    /**
     * Update meter
     *
     * @param meter json body
     * @return
     */
    @PutMapping(RestEndpoints.METERS)
    public ResponseEntity<Meter> updateMeter(@RequestBody Meter meter, @RequestParam(RequestParams.CLIENT_ID) BigInteger clientId) {

        MeterResponse meterResponse = meterService.updateMeter(meter, clientId);

        return new ResponseEntity<>(meterResponse.getMeter(), meterResponse.getStatus());
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> onValidationError(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
