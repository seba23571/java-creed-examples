/*
 * #%L
 * Using Flyway in an OSGi Environment
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
package com.javacreed.examples.flyway.virgo.internal;

import org.flywaydb.core.Flyway;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ExampleActivator implements BundleActivator {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleActivator.class);

  private final ComboPooledDataSource dataSource = new ComboPooledDataSource();

  @Override
  public void start(final BundleContext context) throws Exception {
    try {
      ExampleActivator.LOGGER.debug("Starting");

      ExampleActivator.LOGGER.debug("Initialising the data source");
      dataSource.setUser("root");
      dataSource.setPassword("root");
      dataSource.setMaxPoolSize(2);
      dataSource.setDriverClass("com.mysql.jdbc.Driver");
      dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dev_flyway");

      ExampleActivator.LOGGER.debug("Initialising Flyway");
      final Flyway flyway = new Flyway();
      flyway.setDataSource(dataSource);

      ExampleActivator.LOGGER.debug("Starting migration");
      flyway.migrate();
      ExampleActivator.LOGGER.debug("Migration Complete");
    } catch (final Throwable e) {
      ExampleActivator.LOGGER.error("Failed to migratethe database", e);
    }
  }

  @Override
  public void stop(final BundleContext context) throws Exception {
    ExampleActivator.LOGGER.debug("Stopping");
    ExampleActivator.LOGGER.debug("Closing the data source");
    dataSource.close();
    ExampleActivator.LOGGER.debug("Done");
  }

}
