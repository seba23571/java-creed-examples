/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Example1 {

    public static void main(final String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            final long startTime = System.nanoTime();
            final List<Future<?>> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                final Future<?> future = executorService.submit(new ExampleWorker(8));
                list.add(future);
            }

            Util.printLog("Waiting for the workers to finish");
            Example1.method1(list, 5, TimeUnit.SECONDS);
            final long finishTime = System.nanoTime();
            Util.printLog("Program finished after: %,d nano seconds", finishTime - startTime);

        } finally {
            executorService.shutdown();
        }
    }

    private static void method1(final List<Future<?>> list, final long timeout, final TimeUnit timeUnit) {
        for (final Future<?> future : list) {
            try {
                future.get(timeout, timeUnit);
            } catch (final TimeoutException e) {
                future.cancel(true);
            } catch (final Exception e) {
                Util.printLog("Failed: %s", e);
            }
        }
    }

}
