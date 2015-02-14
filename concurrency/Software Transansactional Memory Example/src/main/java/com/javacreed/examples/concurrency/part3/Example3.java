package com.javacreed.examples.concurrency.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class Example3 {

    public static final Logger LOGGER = LoggerFactory.getLogger(Example3.class);

    public static void main(String[] args) throws Exception {
        final int limit = 1000000;
        printMemoryValue("Memory", createPojoVersion(limit));
        // printMemoryValue("Memory", createStmVersion(limit));
    }

    private static long cleanMemory() {
        long usedMemory = calculateMemoryUsage()[2];
        while (true) {
            forceGc();
            long memoryAfterGc = calculateMemoryUsage()[2];
            if (Math.abs(usedMemory - memoryAfterGc) < 32) {
                return memoryAfterGc;
            }
            usedMemory = memoryAfterGc;
        }
    }

    private static long createStmVersion(int limit) {
        long initMemory = cleanMemory();
        StmAccount[] array = new StmAccount[limit];
        for (int i = 0; i < limit; i++) {
            array[i] = new StmAccount(i);
        }
        long memoryUsed = cleanMemory() - initMemory;
        return memoryUsed;
    }


    private static long createPojoVersion(int limit) {
        long initMemory = cleanMemory();
        PojoAccount[] array = new PojoAccount[limit];
        for (int i = 0; i < limit; i++) {
            array[i] = new PojoAccount(i);
        }
        long memoryUsed = cleanMemory() - initMemory;
        return memoryUsed;
    }

    private static long[] calculateMemoryUsage() {
        final Runtime runtime = Runtime.getRuntime();
        final long total = runtime.totalMemory();
        final long free = runtime.freeMemory();
        final long used = total - free;
        return new long[]{total, free, used};
    }

    private static void printMemoryValue(String name, long valueInBytes) {
        LOGGER.debug(String.format("%-16s %10d (bytes)", name, valueInBytes));
    }

    private static void forceGc() {
        Object object = new Object();
        final WeakReference<Object> ref = new WeakReference<>(object);
        object = null;
        while (ref.get() != null) {
            System.gc();
        }
    }

}
