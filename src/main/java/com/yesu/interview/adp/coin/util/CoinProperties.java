package com.yesu.interview.adp.coin.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix="coin.properties")
@Configuration
@Data
public class CoinProperties {

    private double [] coins;
    private Map<String, Integer> coinsMap;
}
