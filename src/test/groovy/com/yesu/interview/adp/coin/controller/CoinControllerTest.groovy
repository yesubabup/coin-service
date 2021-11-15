package com.yesu.interview.adp.coin.controller

import com.yesu.interview.adp.coin.CoinServiceApplication
import com.yesu.interview.adp.coin.model.CoinResponse
import com.yesu.interview.adp.coin.model.CountRequest
import com.yesu.interview.adp.coin.service.CoinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

import static io.restassured.RestAssured.given

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CoinServiceApplication.class])
class CoinControllerTest extends Specification{

    @Autowired
    private CoinService coinService

    @LocalServerPort
    private int port

    def "getCoins | Return 400 bad request response | Negative"() {

        given: "Mock CoinsService"
        coinService.getMinCoins(*_) >> new CoinResponse()

        when: "call to getCoins is made"
        def response = given().port(port).get("/coins/qwerty")

        then: "400 BadRequest is thrown"
        response.getStatusCode() == 400
    }

    def "addMoreCoins | Return 404 bad request response | Negative"() {

        CountRequest countRequest = new CountRequest()
        countRequest.setCount("qwerty")

        given: "Mock CoinsService"
        coinService.addMoreCoins(*_) >> new CoinResponse()

        when: "call to addMoreCoins is made"
        def response = given().port(port).post(countRequest.getCount())

        then: "404 BadRequest is thrown"
        response.getStatusCode() == 404
    }

}
