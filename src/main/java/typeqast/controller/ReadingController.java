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
import typeqast.entities.Reading;
import typeqast.entities.response.AggregateReadingResponse;
import typeqast.entities.response.ReadingResponse;
import typeqast.service.ReadingService;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.util.List;

@Validated
@RestController
public class ReadingController {

    private static Logger logger = LoggerFactory.getLogger(ReadingController.class);

    @Autowired
    private ReadingService readingService;

    /**
     * Add new reading to existing meter
     *
     * @param reading json body
     * @param meterId id of meter
     *
     * @return
     */
    @PostMapping(RestEndpoints.READINGS)
    public ResponseEntity<Reading> addReading(@RequestBody Reading reading, @RequestParam(name = RequestParams.METER_ID) BigInteger meterId) {
        logger.info("Received add reading request");

        ReadingResponse readingResponse = readingService.addReading(reading, meterId);

        return new ResponseEntity<>(readingResponse.getReading(), readingResponse.getStatus());

    }


    /**
     * Get a list of all readings
     * @return
     */
    @GetMapping(RestEndpoints.READINGS)
    public ResponseEntity<List<Reading>> getReadings() {
        logger.info("Received get readings request");

        List<Reading> readings = readingService.getReadings();

        return new ResponseEntity<>(readings, HttpStatus.OK);
    }

    /**
     * Get aggregate readings for a year
     *
     * @param meterId optional, id for a specific meter
     *
     * @param year mandatory
     *
     * @return
     */
    @GetMapping(RestEndpoints.READINGS + RestEndpoints.AGGREGATE)
    public ResponseEntity<AggregateReading> getAggregateReading(@RequestParam(name = RequestParams.METER_ID, required = false) BigInteger meterId
            , @RequestParam(name = RequestParams.YEAR, required = false) @Min(1900) @Max(2021) Integer year) {
        logger.info("Received get aggregate readings request");

        AggregateReadingResponse aggregateReadingResponse = readingService.getAggregateReadings(year, meterId);

        return new ResponseEntity<>(aggregateReadingResponse.getAggregateReading(), aggregateReadingResponse.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> onValidationError(Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
