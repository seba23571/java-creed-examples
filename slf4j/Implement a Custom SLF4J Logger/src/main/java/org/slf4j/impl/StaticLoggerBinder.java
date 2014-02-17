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
package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import com.javacreed.examples.slf4j.SimpleLoggerFactory;

public class StaticLoggerBinder implements LoggerFactoryBinder {

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public static String REQUESTED_API_VERSION = "1.7.5";

    private static final String loggerFactoryClassStr = SimpleLoggerFactory.class.getName();

    public static final StaticLoggerBinder getSingleton() {
        return StaticLoggerBinder.SINGLETON;
    }

    private final ILoggerFactory loggerFactory;

    private StaticLoggerBinder() {
        loggerFactory = new SimpleLoggerFactory();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return StaticLoggerBinder.loggerFactoryClassStr;
    }
}
