/*
 * #%L
 * Bulk Mail Java Application
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
package com.javacreed.examples.mail;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  public static void main(final String[] args) {
    Main.LOGGER.debug("Starting application");
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final MainPresenter presenter = new MainPresenter();
        presenter.setExecutionContext(new DefaultExecutionContext());
        presenter.setView(new MainView());
        presenter.start();
      }
    });
  }

  public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
}
