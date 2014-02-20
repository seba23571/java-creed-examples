package com.javacreed.examples.concurrency;

public class Util {

    public static void printLog(final String message, final Object... params) {
        System.out.printf("[%tF %<tT] [%s] %s%n", System.currentTimeMillis(), Thread.currentThread().getName(), String.format(message, params));
    }

    private Util() {};
}
