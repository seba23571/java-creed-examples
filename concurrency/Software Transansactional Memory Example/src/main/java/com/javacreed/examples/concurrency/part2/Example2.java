package com.javacreed.examples.concurrency.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Example2.class);

    public static void main(String[] args) {
        Account a = new Account(10);
        Account b = new Account(10);

        a.transferBetween(b, 5);
        LOGGER.debug("Account (a) {}", a);
        LOGGER.debug("Account (b) {}", b);

        try {
            a.transferBetween(b, 20);
        } catch (IllegalStateException e) {
            LOGGER.warn("Failed to transfer money");
        }

        LOGGER.debug("Account (a) {}", a);
        LOGGER.debug("Account (b) {}", b);
    }

}
