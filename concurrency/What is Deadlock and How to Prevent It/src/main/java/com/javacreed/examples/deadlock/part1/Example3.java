package com.javacreed.examples.deadlock.part1;

import java.util.ArrayList;
import java.util.List;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example3 {

    public static double calculateAverage(final List<Integer> list) {
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i);
            // This will cause the bug to manifest
            ThreadUtils.silentSleep(1);
        }

        return total / list.size();
    }

    public static void main(final String[] args) throws Exception {
        // Used as a lock instead of the list itself
        final Object lock = new Object();
        
        final List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(8);
        list.add(10);

        final Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    final double average = Example3.calculateAverage(list);
                    ThreadUtils.log("Average: %.2f", average);
                }
            }
        }, "Thread-A");
        threadA.start();

        final Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < list.size(); i++) {
                        list.set(i, list.get(i) * 2);
                    }
                }
            }
        }, "Thread-B");
        threadB.start();

        /* Wait for the threads to stop */
        threadA.join();
        threadB.join();
    }

}
