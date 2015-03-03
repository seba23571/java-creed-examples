package com.javacreed.examples.multiverse.part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Example1.class);

    public static void main(final String[] args) {
        final Member a = new Member();
        a.setName("Albert Attard");
        Example1.LOGGER.debug("Create a new member {}", a);

        final Group g = new Group();

        Example1.LOGGER.debug("Add new member");
        g.addMember(a);

        Example1.LOGGER.debug("Size of group: {}", g.size());
    }
}
