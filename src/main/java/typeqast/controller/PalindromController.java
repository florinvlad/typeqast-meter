package typeqast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import typeqast.constants.RequestParams;
import typeqast.constants.RestEndpoints;
import typeqast.entities.dto.AddressDTO;
import typeqast.service.PalindromService;

import java.math.BigInteger;

@RestController
public class PalindromController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final PalindromService palindromService;

    public PalindromController(PalindromService palindromService) {
        this.palindromService = palindromService;
    }

    @PostMapping("/palindrom")
    public ResponseEntity<Boolean> checkPalindrom(@RequestParam(name = "input") String input) {

        logger.info("Received create addresses request");

        boolean result = palindromService.checkPalindrome(input);

        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
}
