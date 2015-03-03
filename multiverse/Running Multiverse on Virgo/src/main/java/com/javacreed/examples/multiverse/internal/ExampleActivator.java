/*
 * #%L
 * Running Multiverse on Virgo
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
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
package com.javacreed.examples.multiverse.internal;

import com.javacreed.examples.multiverse.Account;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleActivator implements BundleActivator {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleActivator.class);

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        //try {
            final Account a = new Account(10);
            final Account b = new Account(5);

            LOGGER.debug("Transfer money from account b to account a");
            a.transferBetween(b, 10);
            LOGGER.debug("Transfer complete");

            LOGGER.debug("Both threads finished");
            LOGGER.debug("Account a: {}", a);
            LOGGER.debug("Account b: {}", b);
            LOGGER.debug("Done.");
//        }catch(Throwable e){
//            LOGGER.error("Failed to run example", e);
//        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }
}
