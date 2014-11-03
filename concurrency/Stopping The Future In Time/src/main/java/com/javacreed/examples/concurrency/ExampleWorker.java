/*
 * #%L
 * Stopping The Future In Time
 * $Id:$
 * $HeadURL$
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
package com.javacreed.examples.concurrency;

import java.util.concurrent.TimeUnit;

public class ExampleWorker implements Runnable {

    private final int sleepTime;

    public ExampleWorker(final int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        final long startTime = System.nanoTime();
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(sleepTime));
            Util.printLog("Finished");
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            final long interruptedAfter = System.nanoTime() - startTime;
            Util.printLog("Interrupted after %,d nano seconds", interruptedAfter);
        }
    }

}
