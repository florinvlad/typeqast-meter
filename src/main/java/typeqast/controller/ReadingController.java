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
import typeqast.entities.Reading;
import typeqast.service.ReadingService;

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
     * @return
     */
    @PostMapping(RestEndpoints.READINGS)
    public ResponseEntity<Reading> addReading(@RequestBody Reading reading, @RequestParam(name = RequestParams.METER_ID) BigInteger meterId) {
        logger.info("Received add reading request");

        Reading resultReading = readingService.addReading(reading, meterId);

        return new ResponseEntity<>(resultReading, HttpStatus.CREATED);

    }


    /**
     * Get a list of all readings
     *
     * @return
     */
    @GetMapping(RestEndpoints.READINGS)
    public ResponseEntity<List<Reading>> getReadings() {
        logger.info("Received get readings request");

        List<Reading> readings = readingService.getReadings();

        return new ResponseEntity<>(readings, HttpStatus.OK);
    }

}
