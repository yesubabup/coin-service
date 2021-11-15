package com.yesu.interview.adp.coin.service;

import com.yesu.interview.adp.coin.exception.InsufficientCoinsException;
import com.yesu.interview.adp.coin.model.CoinResponse;
import com.yesu.interview.adp.coin.util.CoinProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CoinService {

    private final CoinProperties coinProperties;

    // static double coins1[] = coinsMap.keySet().
    public CoinResponse getMinCoins(String bill) {
        log.info("in getMinCoins of CoinService");

        Map<BigDecimal, Integer> result = new LinkedHashMap<>();
        Map<String, Integer> coinsMapCopy =  coinProperties.getCoinsMap().entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

        CoinResponse coinResponse = new CoinResponse();
        int n = coinProperties.getCoinsMap().size();
        BigDecimal bd = new BigDecimal(bill);
        bd.setScale(2, RoundingMode.HALF_UP);

        findMin(bd, n,result);
        log.info(
                "Least amount of coins  for " + bill + ": ");
        coinResponse.setGivenBill(bill);
        log.info("Given  bill  " + bill + ": ");


        BigDecimal sum = new BigDecimal("0.00");
        for (Map.Entry<BigDecimal, Integer> coinCount : result.entrySet()) {
            log.info(coinCount.getKey() + " [" + coinCount.getValue()+"], ");
            BigDecimal count = new BigDecimal(Double.toString(Double.valueOf(coinCount.getValue())));
            sum = sum.add(coinCount.getKey().multiply(count));
        }
        coinResponse.setCoins(result);
        log.info(" Total  :" + sum);
        if(sum.compareTo(bd)==0) {
            log.info('\n'+ " Total  :" + sum);
        }else{
            log.error("No more Coins are available");
            //Error Throw
            coinProperties.setCoinsMap(coinsMapCopy);
            throw new InsufficientCoinsException();
        }
        return coinResponse;
    }

    boolean findMin(BigDecimal V, int n,Map<BigDecimal, Integer> result )
    {
        if((V.doubleValue()<0.01) || 0==n){
            return false;
        }

        BigDecimal coin = value(coinProperties.getCoins()[n-1],V);
        if(null!=coin){
            V = V.subtract(coin);
            Integer noOfCoins = result.get(coin);
            if (null != noOfCoins) {
                result.put(coin, ++noOfCoins);
            } else {
                result.put(coin, 1);
            }
            return  findMin(V, n,result);
        }else{
            return findMin(V,--n,result);
        }

    }

    private BigDecimal value(Double coin, BigDecimal bill){

        BigDecimal bdCoin= new BigDecimal(Double.toString(coin));
        bdCoin.setScale(2, RoundingMode.HALF_UP);

        if(bill.compareTo(bdCoin)>=0){
            int count =  coinProperties.getCoinsMap().get(String.valueOf(coin));
            if(count>0){
                coinProperties.getCoinsMap().put(String.valueOf(coin),count-1);
                return  bdCoin;
            }
        }
        return null;
    }

    public String addMoreCoins(String count) {
        coinProperties.getCoinsMap().replaceAll((k,v)->v=Integer.valueOf(count));
        return count;
    }
}
