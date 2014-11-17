/*
 * #%L
 * What is Race Condition and How to Prevent It?
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.cuncurrency.part2;

import java.util.ArrayList;
import java.util.List;

public class Example3 {

    public static void main(final String[] args) throws Exception {
        for (int run = 0, numberOfThreads = 100; run < 1000; run++) {
            System.out.printf("Run %05d.....", run + 1);
            final Data data = new Data();

            final List<Thread> threads = new ArrayList<>(numberOfThreads);
            for (int i = 0; i < numberOfThreads; i++) {
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (data) {
                            final int value = data.getValue();
                            data.setValue(value + 1);
                        }
                    }
                });
                thread.start();
                threads.add(thread);
            }

            for (final Thread thread : threads) {
                thread.join();
            }

            if (data.getValue() == numberOfThreads) {
                System.out.println("Passed");
            } else {
                System.out.printf("Failed with value %d instead of %d%n", data.getValue(), numberOfThreads);
                break;
            }
        }
    }

}
