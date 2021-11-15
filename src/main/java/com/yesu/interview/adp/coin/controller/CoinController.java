package com.yesu.interview.adp.coin.controller;


import com.yesu.interview.adp.coin.model.CoinResponse;
import com.yesu.interview.adp.coin.model.CountRequest;
import com.yesu.interview.adp.coin.service.CoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/coins")
@Slf4j
@Validated
public class CoinController {

    @Autowired
    private CoinService coinService;

    @GetMapping("/{bill}")
    public ResponseEntity<CoinResponse> getMinCoins(@PathVariable("bill")
                                                    @Pattern(regexp = "1|2|5|10|20|50|100",
                                                            message = "Please provide valid bill as number " +
                                                                    "to get respective change")
                                                            String bill ){
        log.info("in getMinCoins of CoinController");
        return ResponseEntity.ok(coinService.getMinCoins(bill));
    }

    @PostMapping
    public ResponseEntity<String> addMoreCoins(@RequestBody CountRequest countRequest) {
        log.info("in addMoreCoins of CoinController");
        if(isNumeric(countRequest.getCount())){
            return ResponseEntity.ok("Successfully added coins:"+coinService.addMoreCoins(countRequest.getCount()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid count to get more coins!!");
        }
    }

    /*
       *
        This method is used to validate user input. User inputs other than number is will throw an error.
       *
       */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
