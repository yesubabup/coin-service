package com.yesu.interview.adp.coin.service

import com.yesu.interview.adp.coin.exception.InsufficientCoinsException
import com.yesu.interview.adp.coin.util.CoinProperties
import spock.lang.Shared
import spock.lang.Specification

class CoinServiceUnitTest extends Specification{

    CoinService service
    @Shared
    CoinProperties properties
    def setupSpec() {
        // Initialize result

        properties = new CoinProperties()
        def coins =  [0.01, 0.05, 0.10, 0.25] as double[]
        def coinsMap = new TreeMap()
        coinsMap.put("0.01", 100);
        coinsMap.put("0.05", 100);
        coinsMap.put("0.1", 100);
        coinsMap.put("0.25", 100);
        print("Unit Test Setup Started")
        properties.setCoins(coins)
        properties.setCoinsMap(coinsMap)
    }

    def setup() {
        service = new CoinService(properties)
    }

    def "getMinCoins | First - Get Min Coins for \$10 bill| Positive"() {
        given: "a bill"

        def bill = "10"

        when: "service is invoked"
        def coinResponse = service.getMinCoins(bill)

        then: "return the least coins denomination"
        coinResponse.getCoins().get(new BigDecimal("0.25")) == new BigDecimal(40)
        coinResponse.getGivenBill() == "10"
    }

    def "getMinCoins | Second - Get Min Coins for \$20 bill| Positive"() {
        given: "a bill"

        def bill = "20"

        when: "service is invoked"
        def coinResponse = service.getMinCoins(bill)

        then: "return the least coins denomination"
        coinResponse.getCoins().get(new BigDecimal("0.25")) == new BigDecimal(60)
        coinResponse.getCoins().get(new BigDecimal("0.1")) == new BigDecimal(50)
        coinResponse.getGivenBill() == "20"
    }

    def "getMinCoins | Third - Get Min Coins for \$100 bill| Negative"() {
        given: "a bill"

        def bill = "100"

        when: "service is invoked"
        def coinResponse = service.getMinCoins(bill)

        then: "throw insufficient coins exception"
        thrown(InsufficientCoinsException)
    }

}
