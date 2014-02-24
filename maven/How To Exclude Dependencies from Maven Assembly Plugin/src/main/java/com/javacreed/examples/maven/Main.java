package com.javacreed.examples.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        Main.LOGGER.debug("My Application uses SLF4J");
    }
}