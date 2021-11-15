package com.yesu.interview.adp.coin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class CoinResponse {

    /**
     * BigDecimal - CoinDenomination
     * Integer - Number of Coins of that particular CoinDenomination
     *
     * Eg:., 0.25 - 40
     *
     */
    private Map<BigDecimal, Integer> coins;
    private String givenBill;

}
