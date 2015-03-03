package com.javacreed.examples.multiverse.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Example3.class);

    public static void main(final String[] args) {
        final Group g = new Group();
        g.addMember(new Member("Albert Attard"));
        g.addMember(new Member("Mary Vella"));
        g.addMember(new Member("Joe Borg"));
        Example3.LOGGER.debug("Size of group: {}", g.size());
        Example3.LOGGER.debug("Size of group: {}", g);
    }
}
