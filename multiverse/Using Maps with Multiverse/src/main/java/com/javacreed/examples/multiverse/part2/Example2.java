package com.javacreed.examples.multiverse.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Example2.class);

    public static void main(final String[] args) {
        final Group g = new Group();
        g.addMember(new Member("Albert Attard"));
        g.addMember(new Member("Mary Vella"));
        g.addMember(new Member("Joe Borg"));
        Example2.LOGGER.debug("Size of group: {}", g.size());
        
        // The toString() does not work
        Example2.LOGGER.debug("Size of group: {}", g);
    }
}
