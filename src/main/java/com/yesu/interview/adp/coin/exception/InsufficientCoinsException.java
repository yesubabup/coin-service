package com.yesu.interview.adp.coin.exception;

public class InsufficientCoinsException extends RuntimeException{

    public InsufficientCoinsException() {
        super("Insufficient Coins, Please try again later");
    }
}
